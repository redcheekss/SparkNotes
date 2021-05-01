import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

public class PgTest {
    private static final String DDL_FORMAT =
            "CREATE TABLE t_result(\n"
                    + "id INTEGER\n"
                    + ") WITH (\n"
                    + "  'connector.type'='jdbc',\n"
                    + "  'connector.url'='jdbc:postgresql://pgm-bp15se465c3cmau8168190.pg.rds.aliyuncs.com:1921/ods' "
                    + "  'password'='cg9vULcv9HqoPVmwjTKL' "
                    + "  'username'= 'ods'  "
                    + "  'connector.table'=' public.ods_v01_addedcustomdataentry_node '\n"
                    + ")";
    public static void main(String[] args) {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        EnvironmentSettings envSettings =
                EnvironmentSettings.newInstance().useBlinkPlanner().inStreamingMode().build();
        StreamTableEnvironment tEnv = StreamTableEnvironment.create(env, envSettings);
        tEnv.executeSql(DDL_FORMAT);
        tEnv.executeSql(sql);


        tEnv.sqlQuery(
                "select id from t_user")
                .executeInsert("t_result");


        // register the DataSet as a view "WordCount"
//        tEnv.createTemporaryView("WordCount", input, $("word"), $("frequency"));
    }

    final public static String sql="CREATE TABLE t_user (\n" +
            "  id integer\n" +
            ") WITH (\n" +
            "  'connector' = 'kafka',\n" +
            //"  'connector.version' = 'universal',\n" +
            "  'topic' = 'c-test',\n" +
            //"  'properties.zookeeper.connect' = 'ha01:2181',\n" +
            "  'properties.bootstrap.servers' = 'ha01:9092',\n" +
            "  'properties.group.id' = 'g1-test',\n" +
            "  'properties.ENABLE_AUTO_COMMIT_CONFIG'='true' ," +
            "  'scan.startup.mode' = 'earliest-offset',\n" +
            "  'format' = 'json'\n" +
            //"  'format.json.schema' = 'true' \n" +
            ")\n";



}
