final PLATFORMS = ['Amba', 'SStar']
folder('packages') {
    displayName('Packages')
    description('Folder for packages')
}
PLATFORMS.each { platform ->
    pipelineJob("packages/Theone-$platform-test") {
        definition {
            cps {
                // add "this" to fix "Scripts not permitted"
                // https://issues.jenkins.io/browse/JENKINS-45778
                script(this.readFileFromWorkspace('build-theone.groovy'))
                sandbox()
            }
        }
        parameters {
            stringParam('platform', "$platform", "$platform")
        }
    }
}
