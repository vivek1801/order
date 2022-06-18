pipeline {
  agent any
  stages {
    stage('Checkout') {
      parallel {
        stage('Checkout') {
          steps {
            echo 'Pulling the latest code from master'
            git(url: 'https://del.tools.publicis.sapient.com/bitbucket/scm/teti2/order.git', branch: 'master', changelog: true, credentialsId: '58de5f1c-6d14-4b17-81bf-d85139752e60', poll: true)
          }
        }
      }
    }
    stage('Build') {
      steps {
        echo 'Build has started'
        sh 'mvn clean install checkstyle:checkstyle findbugs:findbugs pmd:pmd pmd:cpd cobertura:cobertura -Dcobertura.report.format=xml'
      }
    }
    stage('Code Quality Check') {
      steps {
        echo 'Sonar Code Quality Check'
        sh 'mvn -e -B sonar:sonar -Dsonar.java.source=1.8 -Dsonar.host.url=http://40.117.73.9:9000/ -Dsonar.login=admin -Dsonar.password=admin'
      }
    }
    stage('Build Docker Image') {
      steps {
        echo 'Building Docker Image '
        sh 'pwd'
        sh 'docker build -t containervregistry.azurecr.io/order-0.0.1 --pull=true --file=src/main/docker/Dockerfile .'
      }
    }
    stage('Push Image to Registry') {
      steps {
        sh 'docker login containervregistry.azurecr.io -u ContainerVRegistry -p jFBNVjM15fZdSeO=RsePeqN1EoyMgy=A'
        sh 'docker push containervregistry.azurecr.io/order-0.0.1:latest'
      }
    }
    stage('Deploy to AKS') {
      steps {
        sh 'kubectl apply -f deployment.yml'
      }
    }
    stage('Run Jmeter load Test') {
      steps {
        sh 'while ! httping -qc1 http://13.71.80.42:8081/ ; do sleep 10800 ; done'
        sh '/home/vivtiwar0/apache-jmeter-5.1.1/bin/jmeter -Jjmeter.save.saveservice.output_format=xml -n -t src/main/resources/order_service.jmx -l src/main/resources/JMeter.jtl -j jmeter.log -JUSERS=10 -JRAMPUP=10 -JLOOPS=100'
      }
    }
    stage('Uploading ') {
      steps {
        withAWS(region: 'us-east-2', credentials: '4fa99426-0409-46ab-9dc6-acf8ffcae450') {
          s3Delete(bucket: 'tillstestjob', path: '**/*')
          s3Upload(bucket: 'tillstestjob', workingDir: 'src/main/resources/', includePathPattern: '*.jtl')
        }

      }
    }
  }
}