 // execute SQL and fetch data from MDL
      val weeksummary_fl_time_1 = ExtractSparkUtil.getDataFromMDL(rep_sql, parameters)

      val windowSpecRank = Window.partitionBy(col("demo_name")).orderBy(col("demo_name").desc)
      val windowexpression = expr("percentile_approx(last_dock_ts,0.5)")

      val weeksummary_fl_time2_2 = weeksummary_fl_time_1.withColumn("last_dock_ts_percentile",windowexpression.over(windowSpecRank))
      println(s"showing percentile")
      weeksummary_fl_time2_2.show(false)




      //running sql for R058 fl timing

      str_sql_file = sc.textFile(parameters.sql_file_path + "/R058_fl_time.sql").collect().mkString(" ");

      // replace variables based on input parameters
      rep_sql = str_sql_file
        .replace(":edw_schema_name", parameters.edw_schema_name)
        .replace(":mch_schema_name", parameters.mch_schema_name)
        .replace(":market",parameters.args_map("audio.markets"))
        .replace(":demo", parameters.args_map("audio.demoName"))
        .replace(":trgt_demo", parameters.args_map("audio.trgtdemoName"))
        .replace(":date_dim_id_partition_id", partitionids)

      //      println(rep_sql)

      // execute SQL and fetch data from MDL
      val R058_fl_time_1 = ExtractSparkUtil.getDataFromMDL(rep_sql, parameters)
      val R058_fl_time_2 = R058_fl_time_1
        .withColumn("median_last_dock_ts",expr("percentile_approx(last_dock_ts,0.5)").over(Window.partitionBy(col("demo_name")).orderBy(col("demo_name").desc)))
        .withColumn("median_last_media_date",expr("percentile_approx(media_date,0.5)").over(Window.partitionBy(col("demo_name")).orderBy(col("demo_name").desc)))

      println(s"showing R058 fl time with median")
      R058_fl_time_2.show(false)

      val windowexpression2 = expr("percentile_approx(last_dock_ts,0.5)")


      val R058_fl_time_3 = R058_fl_time_1.groupBy("demo_name","week_day_type").agg(windowexpression2)
      println(s"showing R058 fl time with median with group by")
      R058_fl_time_3.show(false)