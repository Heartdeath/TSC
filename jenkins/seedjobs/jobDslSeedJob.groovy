// Example from https://jenkinsci.github.io/job-dsl-plugin/

def svnurl = 'https://coconet-svn-fs-01.fs.capgemini.com/svn/repos/toolscenter/trunk/tsc/'
for(i in 0..10) {
  job("Job-DSL-Tutorial-1-Test-${i}") {
    scm {
      git {
        remote {
          url(svnurl)
        }
        extensions {
          cleanAfterCheckout()
        }
      }
    }
    triggers {
        scm('15/H * * * *')
        githubPush()
    }
    steps {
      maven {
        goals('clean')
        goals('verify')
        goals("test -Dtest.suite=${i}")
        mavenOpts('-Xms256m')
        mavenOpts('-Xmx512m')
        properties(skipTests: true)
        mavenInstallation('Maven 3.3.3')
      }
    }
  }
}

listView('Seed Jobs') {
    description('')
    filterBuildQueue()
    filterExecutors()
    jobs {
        regex(/.*seed-job.*/)
    }
    columns {
        status()
        buildButton()
        weather()
        name()
        lastSuccess()
        lastFailure()
        lastDuration()
    }
}

listView('Job DSL Tutorial') {
    description('')
    filterBuildQueue()
    filterExecutors()
    jobs {
        regex(/.*Tutorial.*/)
    }
    columns {
        status()
        buildButton()
        weather()
        name()
        lastSuccess()
        lastFailure()
        lastDuration()
    }
}
