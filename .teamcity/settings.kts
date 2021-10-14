import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.maven
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs
import jetbrains.buildServer.configs.kotlin.v2019_2.vcs.GitVcsRoot

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2021.1"

project {

    vcsRoot(GitGithubComFalconowExampleTeamcityGitRefsHeadsMaster1)

    buildType(BuildDeploy)
    buildType(First)
    buildType(RtdyjfuioliohklOp)

    params {
        password("env.custom_prop", "zxx1e5b4ad8a1cdfeaef320bc3a515bf3c6", readOnly = true)
    }
}

object BuildDeploy : BuildType({
    name = "Build&Deploy"

    artifactRules = "+:target/*.jar"
    publishArtifacts = PublishMode.SUCCESSFUL

    vcs {
        root(DslContext.settingsRoot)
    }

    steps {
        maven {
            name = "Make Some Test"

            conditions {
                doesNotContain("teamcity.build.branch", "master")
            }
            goals = "clean test"
            runnerArgs = "-Dmaven.test.failure.ignore=true"
            userSettingsSelection = "custom"
        }
        maven {
            name = "Deploy to Nexus"

            conditions {
                contains("teamcity.build.branch", "master")
            }
            goals = "clean package deploy"
            runnerArgs = "-Dmaven.test.failure.ignore=true"
            userSettingsSelection = "custom"
        }
    }

    triggers {
        vcs {
        }
    }
})

object First : BuildType({
    name = "first"

    params {
        param("custom_chekout_dir", "123")
    }

    vcs {
        checkoutDir = "%custom_chekout_dir%"
    }

    steps {
        script {
            name = "echo variables"
            scriptContent = """
                echo 1
                echo %custom_chekout_dir%
                export custom_prop=new
                echo ${'$'}custom_prop
            """.trimIndent()
        }
    }
})

object RtdyjfuioliohklOp : BuildType({
    name = "rtdyjfuioliohkl;op"
})

object GitGithubComFalconowExampleTeamcityGitRefsHeadsMaster1 : GitVcsRoot({
    name = "git@github.com:falconow/example-teamcity.git#refs/heads/master (1)"
    url = "git@github.com:falconow/example-teamcity.git"
    branch = "refs/heads/master"
    branchSpec = "refs/heads/*"
    authMethod = uploadedKey {
        userName = "git"
        uploadedKey = "git"
    }
    param("secure:password", "")
})
