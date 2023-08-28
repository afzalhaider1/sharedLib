@Library('sharedLib') _

def pipelineConfig = [
    gitRepoUrl: 'https://github.com/afzalhaider1/OT-Microservices.git',
    pathToPomFile: 'salary',
    sonarProjectKey: 'OT-Microservices',
    sonarProjectName: 'OT-Microservices',
    nexusUrl: '172.31.31.154:8081',
    groupId: 'org.springframework.boot',
    version: '2.7.4',
    repository: 'OT-Microservices',
    credentialsId: 'nexus',
    artifactId: 'spring-boot-starter-parent',
    artifactFileNameAndPath: 'salary/target/salary-0.3.0-RELEASE.jar',
    artifactType: 'jar',
    notificationEmail: 'maiafzal@gmail.com'
]

buildAndPublish(pipelineConfig)
