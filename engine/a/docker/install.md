
# CASSANDRA
docker pull cassandra:3.11.14
docker run -it -p 9042:9042 --name cass_cluster cassandra:3.11.14

## run in csh(cassandra shell)
CREATE KEYSPACE djdb WITH REPLICATION = {
    'class': 'SimpleStrategy', 'replication_factor': 2 };

# KAFKA util install and Command
## download
curl https://archive.apache.org/dist/kafka/3.2.0/kafka_2.13-3.2.0.tgz -o kafka.tgz

## extract
tar -xzf kafka.tgz

cd <kafka directory>

# Topic create
bin/kafka-topics.sh --create --topic quickstart-events --bootstrap-server localhost:9092

# Topic describe
bin/kafka-topics.sh --describe --topic quickstart-events --bootstrap-server localhost:9092

# Producer start
bin/kafka-console-producer.sh --topic quickstart-events --bootstrap-server localhost:9092

# Consumer start
bin/kafka-console-consumer.sh --topic quickstart-events --from-beginning --bootstrap-server localhost:9092


