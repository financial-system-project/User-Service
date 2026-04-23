pipeline {
    agent any

    environment {
        IMAGE_NAME = "mahendra3/user"
        USER_NAME = "mahendra3"
        CHART_NAME = "user-service"
    }

    tools {
        maven 'maven_3.9'
    }

    stages{

        stage(' 1: cloning repository from GitHub') {
            steps {
                git branch: 'main' ,
                url: ' https://github.com/financial-system-project/User-Service.git '
            }
        }

        stage('2: Maven Build') {
            steps {
                sh '''
                    java -version
                    mvn -version
                    mvn clean install -DskipTests
                '''
            }
        }

        stage(" 3: Building Docker Image ") {
            steps {
                script{
                    docker_image = docker.build "$IMAGE_NAME:latest"
                }

            }
        }

        stage(" 4: Pushing Docker image to DockerHub") {

            steps {
                withCredentials([usernamePassword(credentialsId: 'DockerPwd',
                                  usernameVariable: 'USER',
                                  passwordVariable: 'PASS')]) {
                    sh '''
                        echo $PASS | docker login -u $USER --password-stdin
                        docker push $IMAGE_NAME
                    '''
                }
            }
        }

        stage(' 5: clean Docker Images') {
            steps {
                script {
                    sh 'docker container prune -f'
                    sh 'docker image prune -f'
                }
            }
        }

        stage('6: Get Chart Version') {
            steps {
                script {
                    env.CHART_VERSION = sh(
                        script: "grep '^version:' helm-charts/Chart.yaml | awk '{print \$2}'",
                        returnStdout: true
                    ).trim()
                }
                echo "Chart Version: ${CHART_VERSION}"
            }
        }

            stage('7: Build & Push Helm Chart') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'DockerPwd',
                    usernameVariable: 'DOCKER_USERNAME',
                    passwordVariable: 'DOCKER_PASSWORD'
                )]) {
                    sh '''
                        cd helm-charts/

                        helm package .

                        echo "$DOCKER_PASSWORD" | helm registry login registry-1.docker.io \
                            -u "$DOCKER_USERNAME" --password-stdin

                        helm push ${CHART_NAME}-${CHART_VERSION}.tgz \
                            oci://registry-1.docker.io/$USER_NAME
                    '''
                }
            }
        }


    }

}