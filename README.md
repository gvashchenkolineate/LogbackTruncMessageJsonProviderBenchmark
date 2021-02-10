# JMH Benchmark for custom JsonProvider for Logback

Custom Logback JsonProvider truncates etremely long log lines ('message' field)

## How to run benchmark

      cd ./test
      mvn clean verify
      java -jar target/benchmarks.jar

## Benchmark results

      Benchmark                                                   (messageLen)  Mode  Cnt   Score    Error  Units
      TruncMessageJsonProviderBenchmark.writeWithDefaultProvider           500  avgt    3  ≈ 10⁻⁹            s/op
      TruncMessageJsonProviderBenchmark.writeWithDefaultProvider       2097151  avgt    3  ≈ 10⁻⁹            s/op
      TruncMessageJsonProviderBenchmark.writeWithDefaultProvider       2097153  avgt    3  ≈ 10⁻⁹            s/op
      TruncMessageJsonProviderBenchmark.writeWithDefaultProvider       4194304  avgt    3  ≈ 10⁻⁹            s/op
      TruncMessageJsonProviderBenchmark.writeWithTruncProvider             500  avgt    3  ≈ 10⁻⁹            s/op
      TruncMessageJsonProviderBenchmark.writeWithTruncProvider         2097151  avgt    3  ≈ 10⁻⁹            s/op
      TruncMessageJsonProviderBenchmark.writeWithTruncProvider         2097153  avgt    3   0.001 ±  0.001   s/op
      TruncMessageJsonProviderBenchmark.writeWithTruncProvider         4194304  avgt    3   0.001 ±  0.001   s/op

## Conclusion

Performance is affected only when writing extremely long messages (of length > max length specified for truncating provider).
It's acceptable in order to avoid log shipping service failures.