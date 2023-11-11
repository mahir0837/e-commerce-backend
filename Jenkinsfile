pipeline {
    agent any

    stages {
        stage('Build and Push Docker Image') {
            environment {
                DOCKER_IMAGE = 'mahirsarac/e-commerce-backend-server:latest' // Replace with your Docker repository and image name
            }
            steps {
                script {
                    // Build the Docker image
                    sh 'docker build -t $DOCKER_IMAGE -f Dockerfile .'

                    // Push the Docker image to the registry
                    sh 'docker push $DOCKER_IMAGE'
                }
            }
        }
    }
}
