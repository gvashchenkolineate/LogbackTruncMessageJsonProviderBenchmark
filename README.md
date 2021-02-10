# JMH Benchmark for custom JsonProvider for Logback

Custom Logback JsonProvider truncates etremely long log lines ('message' field)

## How to run benchmark

      cd ./test
      mvn clean verify
      java -jar target/benchmarks.jar

## Benchmark results

      Benchmark                                                   (messageLen)  Mode  Cnt   Score   Error  Units
      TruncMessageJsonProviderBenchmark.writeWithDefaultProvider       2097151  avgt    2  ≈ 10⁻⁹           s/op
      TruncMessageJsonProviderBenchmark.writeWithDefaultProvider       2097153  avgt    2  ≈ 10⁻⁹           s/op
      TruncMessageJsonProviderBenchmark.writeWithDefaultProvider       4194304  avgt    2  ≈ 10⁻⁹           s/op
      TruncMessageJsonProviderBenchmark.writeWithTruncProvider         2097151  avgt    2  ≈ 10⁻⁸           s/op
      TruncMessageJsonProviderBenchmark.writeWithTruncProvider         2097153  avgt    2   0.002           s/op
      TruncMessageJsonProviderBenchmark.writeWithTruncProvider         4194304  avgt    2   0.002           s/op
