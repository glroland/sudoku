pipeline {
  agent any
  stages {
    stage('Checkout Scm') {
      steps {
        git 'https://github.com/glroland/sudoku.git'
      }
    }

    stage('Maven Build') {
      steps {
        sh 'mvn clean package'
      }
    }

    stage('Scan for OWASP Issues in Dependencies') {
      steps {
        dependencyCheck additionalArguments: ''' 
                    -o "./" 
                    -s "./"
                    -f "ALL" 
                    --prettyPrint''', odcInstallation: 'OWASP Dependency-Check'

        dependencyCheckPublisher pattern: 'dependency-check-report.xml'
      }
    }

  }
  tools {
    jdk 'java-11'
  }
  post {
    always {
      step(followSymlinks: false, artifacts: 'sudoku-svc/target/sudoku-svc-1.0.jar,dependency-check-report.xml', $class: 'ArtifactArchiver')
    }

  }
  options {
    buildDiscarder(logRotator(numToKeepStr: '3'))
  }
}

