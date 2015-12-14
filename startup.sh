
echo "Killing Spring Boot project TagIt."

for pid in $(ps -ef | grep "spring" | awk '{print $2}'); do kill -9 $pid; done

echo "Clone from github."

git pull

echo " "

sleep 5

mvn clean install

sleep 5

nohup mvn spring-boot:run &

tail -f nohup.out