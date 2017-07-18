# scdf-simulation

Experimenting with https://github.com/spring-cloud/spring-cloud-sleuth with four Spring Cloud Stream applications.  These simulate the flow of a stream and a 'tap' in SCDF.  That is

```
stream1 = source | transformer | sink
stream2 = :stream1.source > tap
```

The transformer app has a sleep of 50ms to simulate processing and the tap has a sleep of 20 ms.  Note, the sink does not sleep.

Steps

# Install RabbitMQ

# Install and run Zipkin
```
wget -O zipkin.jar 'https://search.maven.org/remote_content?g=io.zipkin.java&a=zipkin-server&v=LATEST&c=exec'
java -jar zipkin.jar
```

* Use the `build.sh` script to build the four applications
```
$ ./build.sh
```

# Run the apps

* Run the listener applications, each command in a separate shell window.

```
$ java -jar sink/target/scdf-sink-sleuth-0.1.0.jar

$ java -jar transformer/target/scdf-transformer-sleuth-0.1.0.jar 

$ java -jar tap/target/scdf-tap-sleuth-0.1.0.jar
```

* Now run the 'source`' which will send one message

```
$ java -jar source/target/scdf-source-sleuth-0.1.0.jar 
```

# View the Traces

* Open the Zipking UI at `http://localhost:9411/zipkin/` and press the find traces button.
You should see one trace with a two sends and three receives.  The first send is from the `emailSource` to the `emailTransformer`, the second send is from the `emailTransformer` to the `emailSink.  The second send is 'inside' the overall recieve of the transformer app.  One receive is from the source to the transformer and another receive is from the source to the tap.  The last receive is from the transformer to the sink.  If you run the source multiple times, the length of some of the processing goes down as JIT will kick in.

If you click on the light blue rectangles in the trace diagram, the payload is listed as a tag property.  The transformer is the most interesting one to look at as you can see the input and output payloads.

The white spaces between the bars is the time spent on the wire and inside the broker.

Each application has an instance of `ProvenanceInterceptor` that adds key-value pairs to the trace.  It is ordered to execute after the `IntegrationTraceChannelInterceptor` that is part of Sleuth and autoconfigured.

![Zipkin Trace view](/zipkin-trace-ui.png)

The [Trace JSON](trace.json) file can be used to build other views that show the topology over the timeline.
