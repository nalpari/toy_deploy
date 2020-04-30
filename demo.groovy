def ROLLBACK_PROCESS = false

pipeline {
    agent any

    stages {
		stage('Preparation') {
			steps {
				echo '준비작업을 진행합니다.'
				// sh '/app/build/work/maven/bin/mvn -version'
				sh 'ls -al'
			}
		}
		stage('Checkout') {
			steps {
				echo "레파지토리를 체크아웃합니다."
				checkout changelog: false, poll: false, scm: [
					$class: 'GitSCM',
					branches: [[
						name: "master"
					]],
					doGenerateSubmoduleConfigurations: false,
					extensions: [[
						$class: "WipeWorkspace"
					], [
						$class: "CleanBeforeCheckout"
					]],
					submoduleCfg: [],
					userRemoteConfigs: [[
                        credentialsId: 'nalpari',
						url: "https://github.com/nalpari/demo.git"
					]]
				]
			}
		}
		stage('build') {
            steps {
				echo '프로젝트를 빌드합니다.'
				sh '''
                    /root/lib/apache-maven-3.6.3/bin/mvn clean build -Dmaven.test.skip=true
					ls -al
				'''
			}
		}
    }
}