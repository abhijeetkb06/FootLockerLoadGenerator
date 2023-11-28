package main.multi.threaded.data.load.generator;


import com.couchbase.client.core.error.CouchbaseException;
import com.couchbase.client.core.error.DocumentExistsException;
import com.couchbase.client.core.error.DocumentNotFoundException;
import com.couchbase.client.java.*;
import com.couchbase.client.java.codec.RawJsonTranscoder;
import com.couchbase.client.java.codec.RawStringTranscoder;
import com.couchbase.client.java.json.JsonObject;
import com.couchbase.client.java.kv.GetOptions;
import com.couchbase.client.java.kv.GetResult;
import com.couchbase.client.java.kv.UpsertOptions;
import com.couchbase.client.java.query.QueryResult;
import com.couchbase.client.java.query.QueryScanConsistency;
import com.github.javafaker.Faker;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Collectors;

import static com.couchbase.client.java.query.QueryOptions.queryOptions;


public class Producer extends Thread {

    // Load data in queue
    private BlockingQueue<String> tasksQueue;

    public Producer(BlockingQueue<String> tasksQueue) {
        super("TASKS PRODUCER");
        this.tasksQueue = tasksQueue;
    }

    public void run() {

        int id = 1;
        while (true) {
            try {
                String user = "user" + id;
                Faker faker = new Faker();

              /*  std.setId(faker.idNumber().hashCode());
                std.setName(faker.name().fullName());*/

                //Complex JSON sample
//                String content = "{\"order\":{\"orderRequest\":{\"requestType\":\"\",\"requester\":\"9490fb77-6ead-47e9-b347-ff956933e745\",\"requestID\":\"45c151eb-d118-42aa-8751-d479128f02de\",\"requestDate\":\"2022-02-07T22:03:33.906836Z\",\"flRequestId\":\"U20467200000\"},\"orderHeader\":{\"obfOrder\":true,\"companyNumber\":\"34\",\"orgId\":\"FL_NA\",\"segment\":\"COMMON_POOL\",\"sellingChannel\":\"FA_US_ECOMM\",\"channel\":\"WEB\",\"userAgent\":\"Mozilla\\/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit\\/605.1.15 (KHTML, like Gecko) Version\\/15.1 Safari\\/605.1.15\",\"source\":\"CAS\",\"vendorId\":\"34\",\"webOrderNumber\":\"U20467200000\",\"orderDateTime\":\"2022-02-07T22:03:33Z\",\"shipMethod\":\"1\",\"shipMethodDesc\":\"5-6 Business Days\",\"rushFlag\":false,\"giftBoxFlag\":false,\"phoneNumber\":\"7737710363\",\"orderPricing\":{\"currencyIso\":\"USD\",\"baseShippingAmount\":6,\"baseShippingTaxAmount\":0.38,\"shippingAmount\":7.99,\"subTotalAmount\":40,\"taxAmount\":3,\"totalAmount\":50.99,\"discountAmount\":0,\"discountedTotalAmount\":50.99},\"userIPAddress\":\"10.224.19.37\",\"shippingAddress\":{\"city\":\"CHICAGO\",\"firstName\":\"foo\",\"lastName\":\"bar\",\"postalCode\":\"60634-1818\",\"state\":\"IL\",\"streetAddress\":\"5634 W Cullom Ave\",\"countryCode\":\"US\",\"extendedAddress\":\"\",\"country\":\"US\",\"addressLine1\":\"5634 W Cullom Ave\",\"addressLine2\":\"\",\"email\":\"arichmond79@gmail.com\",\"phoneNumber\":\"999-999-9999\",\"addressType\":\"NORMAL\"},\"billingAddress\":{\"city\":\"Cupertino\",\"firstName\":\"Allison\",\"lastName\":\"Richmond\",\"postalCode\":\"95014\",\"state\":\"CA\",\"streetAddress\":\"1 Infinite Loop\",\"countryCode\":\"US\",\"country\":\"US\",\"addressLine1\":\"1 Infinite Loop\",\"email\":\"arichmond79@gmail.com\",\"phoneNumber\":\"7737710363\",\"addressType\":\"NORMAL\"},\"langID\":\"E\",\"shoppingCartId\":\"45c151eb-d118-42aa-8751-d479128f02de\",\"transactionType\":\"NORMAL\"},\"user\":{\"firstName\":\"Allison\",\"lastName\":\"Richmond\",\"email\":\"arichmond79@gmail.com\",\"type\":\"GUEST\",\"id\":\"9490fb77-6ead-47e9-b347-ff956933e745\"},\"fullfillmentGrouping\":[{\"fullfillmentType\":\"SHIP\",\"shippingAmount\":{\"currencyIso\":\"USD\",\"value\":1.99},\"orderLines\":[{\"lineNumber\":1,\"taxCode\":\"PCLO-01\",\"shipMethod\":\"1\",\"shipMethodDesc\":\"5-6 Business Days\",\"freeShipping\":false,\"rushFlag\":false,\"quantity\":1,\"s2s\":false,\"inventoryLocation\":\"STORE\",\"product\":{\"name\":\"Converse All Star High Top - Boys' Preschool\",\"sku\":\"43231\",\"size\":\"13.0\",\"color\":\"Black\\/White\",\"brand\":\"Converse\",\"backorderFlag\":false,\"launchSkuFlag\":false,\"presell\":false,\"taxCode\":\"PCLO-01\",\"productDesignator\":\"IL2\",\"productNumber\":\"1287533\",\"productType\":\"REGULAR\",\"sizeDisplayed\":\"13.0\"},\"orderLinePricing\":{\"currencyIso\":\"USD\",\"originalRetailPrice\":40,\"unitPrice\":40,\"subTotalAmount\":40,\"taxAmount\":2.5,\"shippingAmount\":1.99,\"shippingTaxAmount\":0.12,\"totalAmount\":44.61,\"discountAmount\":0,\"discountedTotalAmount\":44.61},\"orderLineReservations\":{\"deliveryDate\":\"2022-02-16\",\"lineId\":\"7dc0b229-4013-3fe4-b2be-a10c58203df3\",\"uom\":\"EACH\",\"itemId\":\"7dc0b229-4013-3fe4-b2be-a10c58203df3\",\"productId\":\"43231-13.0\",\"locationReservationDetails\":[{\"locationId\":\"2957241\",\"locationType\":\"STORE\",\"quantity\":\"1\"}]}}]}]},\"payment\":{\"currencyIso\":\"USD\",\"authorizations\":[{\"paymentType\":\"GIFTCARD\",\"transactionId\":\"14338186387990072025\",\"authCode\":\"334970\",\"transactionDate\":\"2022-02-07T16:03:32.965-06:00\",\"authAmount\":3.6,\"originalOrderNumber\":\"U20467200000\",\"gateway\":\"Valuelink\",\"attributes\":{\"cardTypeDisplay\":\"GIFTCARD\",\"giftCardNumber\":\"7777065385265673\",\"sellerProtectionStatus\":\"ELIGIBLE\"}},{\"paymentType\":\"applepay\",\"paymentVendor\":\"applepay\",\"transactionId\":\"BBMDCV2KLSGLNK82\",\"authCode\":\"030340\",\"transactionDate\":\"2022-02-07T16:03:33.864-06:00\",\"authAmount\":47.39,\"originalOrderNumber\":\"U20467200000\",\"gateway\":\"Adyen\",\"attributes\":{\"authResponse\":\"true\",\"avsCode\":\"2 Neither postal code nor address match\",\"cvvResponse\":\"6 No CVC\\/CVV provided\",\"cardType\":\"3\",\"cardTypeDisplay\":\"AMEX\",\"cardBin\":\"370295\",\"cardLast4\":\"0496\",\"cardAlias\":\"H515377769158412\",\"cardToken\":\"MPN57VZQ76KXWD82\",\"expirationDate\":\"0126\",\"confirmationCode\":\"030340\",\"sellerProtectionStatus\":\"ELIGIBLE\",\"fundingSource\":\"UNKNOWN\"}}]},\"sourceId\":\"45c151eb-d118-42aa-8751-d479128f02de\",\"attemptNumber\":1}";

//                String content = "{ \"sku:"    +id+",price\"  : 199.95,  \"shipTo\" : { \"name\" : \"Bob Brown\",               \"address\" : \"456 Oak Lane\",               \"city\" : \"Pretendville\",               \"state\" : \"HI\",               \"zip\"   : \"98999\" },  \"billTo\" : { \"name\" : \"Alice Brown\",               \"address\" : \"456 Oak Lane\",               \"city\" : \"Pretendville\",               \"state\" : \"HI\",               \"zip\"   : \"98999\" }}";
//                String content = "{ \"sku:"    +id+",price\"  : 199.95,  \"shipTo\" : { \"name: "+faker.name().fullName()+",address\" : \"456 Oak Lane\",               \"city\" : \"Pretendville\",               \"state\" : \"HI\",               \"zip\"   : \"98999\" },  \"billTo\" : { \"name\" : \"Alice Brown\",               \"address\" : \"456 Oak Lane\",               \"city\" : \"Pretendville\",               \"state\" : \"HI\",               \"zip\"   : \"98999\" }}";
                JsonObject content = JsonObject.from(new LinkedHashMap<>());
//                JsonObject content = JsonObject.create()
                content.put("OrderID", faker.number().digit().toString())
                        .put("User", String.valueOf(user))
                        .put("Price", faker.commerce().price())
                        .put("Ordered By", faker.name().fullName())
                        .put("Books", Arrays.asList("programming", "XP", "TDD"))
                        .put("address", JsonObject.create()
                                .put("Street", faker.address().streetAddress())
                                .put("City", faker.address().city())
                                .put("State", faker.address().state())
                                .put("Zip", faker.address().zipCode()))
                        .put("movies", Arrays.asList(
                                JsonObject.create()
                                        .put("title", "Fight Club")
                                        .put("critic", 8.2),
                                JsonObject.create()
                                        .put("title", "Blade Runner")
                                        .put("critic", 9.3),
                                JsonObject.create()
                                        .put("title", "Toys Story")
                                        .put("critic", 8.7)
                        ));

//                CouchbaseConfiguration.usersColl.upsert(user, content);

                CouchbaseConfiguration.ordersColl.upsert(user, content.toString(),
                        UpsertOptions.upsertOptions().transcoder(RawJsonTranscoder.INSTANCE));

                ////////

//                bulkReadCBCatalogReactive(CouchbaseConfiguration.cluster,CouchbaseConfiguration.shoppingCartBucket,CouchbaseConfiguration.shoppingScope,CouchbaseConfiguration.ordersColl);


                // the producer will add an element into the shared queue.
                tasksQueue.put(user);
                System.out.println(getName() + " User added to queue " + user);
				id++;
                System.out.println("@@@@@@@@@ TASK PRODUCED @@@@@@@@ " + tasksQueue.size());
                System.out.println(" Thread Name: " + Thread.currentThread().getName());
            } catch (DocumentExistsException ex) {
                System.err.println("The document already exists!");
            } catch (CouchbaseException ex) {
                System.err.println("Something else happened: " + ex);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void bulkReadCBCatalogReactive(Cluster cluster, Bucket bucket, Scope scope, Collection collection) {
        try {

            ReactiveCluster reactiveCluster = cluster.reactive();
            ReactiveBucket reactiveBucket = bucket.reactive();
            ReactiveScope reactiveScope = scope.reactive();
            ReactiveCollection reactiveCollection = collection.reactive();

            var query = "SELECT meta(c).id FROM `CruiseSearch-magma`.`CruiseSearch`.cbcatalog c WHERE meta(c).id like '%0%' limit 11022";

            QueryResult result = cluster.query(query,
                    queryOptions().adhoc(false).maxParallelism(4).scanConsistency(QueryScanConsistency.NOT_BOUNDED).metrics(false));
            List<String> docsToFetch = result.rowsAsObject().stream().map(s -> s.getString("id")).collect(Collectors.toList());

            long startTime = System.currentTimeMillis();

          /*  List<GetResult> results = Flux.fromIterable(docsToFetch)
                    .flatMap(key -> reactiveCollection.get(key, GetOptions.getOptions().transcoder(RawStringTranscoder.INSTANCE)).onErrorResume(e -> Mono.empty())).collectList().block();
*/
           /* List<GetResult> results = Flux.fromIterable(docsToFetch)
                    .flatMap(key -> reactiveCollection.get(key, GetOptions.getOptions().transcoder(RawStringTranscoder.INSTANCE)).onErrorResume(e -> Mono.empty())).collectList().block();
*/

            //If you want to set a parent for a SDK request, you can do it in the respective *Options:
            //getOptions().parentSpan(OpenTelemetryRequestSpan.wrap(parentSpan))

            /*Span parentSpan = getTracer(openTelemetry).spanBuilder("parentSpan").setNoParent().startSpan();
            System.out.println("In parent method. TraceID : {}"+ parentSpan.getSpanContext().getTraceId());*/

            // Perform bulk read by controlling number of threads in parallel function
            List<GetResult>  results =  Flux.fromIterable(docsToFetch)
                    .parallel(10)
                    .runOn(Schedulers.boundedElastic())
                    .flatMap(key -> reactiveCollection.get(key, GetOptions.getOptions().transcoder(RawStringTranscoder.INSTANCE))
                            .onErrorResume(e -> Mono.empty()))
                    .sequential()
                    .collectList()
                    .block();


            long networkLatency = System.currentTimeMillis() - startTime;
            System.out.println("Total TIME including Network latency in ms: " + networkLatency);

            System.out.println("Total Docs: " + results.size());

            String returned = results.get(0).contentAs(String.class);
            System.out.println("Done" + returned);
        } catch (DocumentNotFoundException ex) {
            System.out.println("Document not found!");
        }
    }
}
