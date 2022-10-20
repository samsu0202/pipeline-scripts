final PLATFORMS = ['Amba', 'SStar']
folder('packages') {
    displayName('Packages')
    description('Folder for packages')
}
PLATFORMS.each { platform ->
    pipelineJob("packages/Theone-$platform") {
        definition {
//             cps {
//                 // add "this" to fix "Scripts not permitted"
//                 // https://issues.jenkins.io/browse/JENKINS-45778
//                 script(this.readFileFromWorkspace('pipelines/build-theone.groovy'))
//                 sandbox()
//             }
            cpsScm {
                scriptPath('pipelines/build-theone.groovy')
            }
        }
        parameters {
            stringParam('platform', "$platform", "$platform")
        }
    }
}
