pipeline{
    agent any
    tools{
        maven 'maven'
    }
        stages{
            stage('build maven')
            steps{
                checkout
                (git code)
                sh 'mvn clean install'
            } 
            stage ('build docker image'){
            steps{
                sh 'docker build -t myimage/deopd integration'
            }
            stage ('push image')
            steps{
                with credentials([string(credentialsID: dockerhub-pwd;variable:'dockerhubpwd')])
                sh 'docker login -u username p${dockerhubpwd}'
                sh 'docker push follwed by image name'
            }
            stage ('deploy to k8s to dev then to uat then to prod')
            steps {
                script{
                    sh 'kubectl apply -f deployementservices.yaml'
                }
            }
        }

            }
        }
    