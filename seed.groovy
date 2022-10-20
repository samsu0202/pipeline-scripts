def VSAAS_PLATFORMS = ['Amba', 'SStar']

pipeline {
    agent any 
    stages {
        stage('Pipeline to seed or update all pipelines') {
            steps {
                script {
                    VSAAS_PLATFORMS.each { platform ->
                        jobDsl scriptText: """
                            pipelineJob("Theone-$platform") {
                                definition {
                                  cpsScm {
                                    scm {
                                      git {
                                        remote {
                                          github('samsu0202/pipeline-scripts')
                                        }
                                      }
                                    }
                                    scriptPath('build-theone.groovy')
                                  }
                                }
                                parameters {
                                  stringParam('platform', "$platform", "$platform")
                                }
                          }""",
                       sandbox: true
                        
                    }
                }
                // jobDsl  targets: ['*.groovy'].join('\n')
//                 echo 123
            }
        }
    }
}
