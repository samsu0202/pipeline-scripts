pipeline {
    agent any 
    stages {
        stage('Pipeline to seed or update all pipelines') {
            steps {
                jobDsl targets: ['seeds/packageSeed.groovy'].join('\n'),
                       removedJobAction: 'DELETE',
                       removedViewAction: 'DELETE',
                       sandbox: true
            }
        }
    }
}
