def call(Map config) {
    pipeline {
        agent any
        parameters {
            booleanParam(name: 'SKIP_CODE_STABILITY', defaultValue: false, description: 'Skip Code Stability Analysis')
            booleanParam(name: 'SKIP_SONARQUBE_CODE_QUALITY_ANALYSIS', defaultValue: false, description: 'Skip SonarQube Analysis')
            booleanParam(name: 'SKIP_CODE_COVERAGE', defaultValue: false, description: 'Skip Code Coverage Analysis')
        }
        stages {
            stage('Code Checkout') {
                steps {
                    git config.gitRepoUrl
                }
            }
            stage('Build') {
                steps {
                    dir(config.pathToPomFile) {
                        sh 'mvn clean package'
                    }
                }
            }
            stage('Parallel Stages') {
                parallel {
                    stage('Code Stability') {
                        when {
                            expression { params.SKIP_CODE_STABILITY != true }
                        }
                        steps {
                            dir(config.pathToPomFile) {
                                sh 'mvn pmd:pmd'
                            }
                        }
                    }
                    stage('SonarQube code quality analysis') {
                        when {
                            expression { params.SKIP_SONARQUBE_CODE_QUALITY_ANALYSIS != true }
                        }
                        steps {
                            withSonarQubeEnv('sonarQube') {
                                dir(config.pathToPomFile) {
                                    sh "mvn sonar:sonar -Dsonar.projectKey=${config.sonarProjectKey} -Dsonar.projectName='${config.sonarProjectName}'"
                                }
                            }
                        }
                    }
                    stage('Code Coverage Analysis') {
                        when {
                            expression { params.SKIP_CODE_COVERAGE != true }
                        }
                        steps {
                            dir(config.pathToPomFile) {
                                sh 'mvn jacoco:report'
                            }
                        }
                    }
                }
            }
            stage('Input') {
                steps {
                    input('Do you want to proceed?')
                }
            }
            stage('Publish Artifacts, If proceed') {
                steps {
                    script {
                        // This stage will be executed only if the user proceeds in the "Input" stage.
                        nexusArtifactUploader(
                            nexusVersion: 'nexus3',
                            protocol: 'http',
                            nexusUrl: config.nexusUrl,
                            groupId: config.groupId,
                            version: config.version,
                            repository: config.repository,
                            credentialsId: config.credentialsId,
                            artifacts: [
                                [artifactId: config.artifactId,
                                 classifier: '',
                                 file: config.artifactFileNameAndPath,
                                 type: config.artifactType]
                            ]
                        )
                    }
                }
            }
        }
        post {
            failure {
                emailext body: "The build failed. Check the console output: ${BUILD_URL}",
                        subject: "[FAILURE] Build failed: ${currentBuild.fullDisplayName}",
                        to: config.notificationEmail
                slackSend(color: "danger", message: "The build failed. Check the console output: ${BUILD_URL}")
            }
            success {
                emailext body: "The build succeeded. View the artifacts: ${BUILD_URL}",
                        subject: "[SUCCESS] Build succeeded: ${currentBuild.fullDisplayName}",
                        to: config.notificationEmail
                slackSend(color: "good", message: "The build succeeded. View the artifacts: ${BUILD_URL}")
            }
        }
    }
}
