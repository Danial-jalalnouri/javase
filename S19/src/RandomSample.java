import java.security.SecureRandom;
import java.util.Random;
import java.util.SplittableRandom;
import java.util.concurrent.ThreadLocalRandom;
import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;

public class RandomSample {
    public static void main(String[] args) {
        randomSample07();
    }

    // java 17
    // high quality, high performance, thread safe > separate
    private static void randomSample07(){

        RandomGenerator l128x1024 = RandomGeneratorFactory.of("L128X1024MixRandom").create();

        long randomLong = l128x1024.nextLong();
        System.out.println("random long: "+ randomLong);

    }

    // java 17
    // factory
    private static void randomSample06(){
        RandomGenerator splittableRandom = RandomGeneratorFactory.of("SplittableRandom").create();

        int randomInt = splittableRandom.nextInt();
        System.out.println("random int: " + randomInt);
    }

    // Java 17
    // Unified interface
    private static void randomSample05(){
        // of Random, SplittableRandom, ..
        RandomGenerator generator = RandomGenerator.of("Random");

        int randomInt = generator.nextInt();
        System.out.println("RandomGenerator (Random) int: " + randomInt);
    }

    // java 7
    // high performance
    // thread safe
    private static void randomSample04(){
        int randomInt = ThreadLocalRandom.current().nextInt();
        System.out.println("ThreadLocalRandom int: " + randomInt);

        int randomIntRange = ThreadLocalRandom.current().nextInt(0,10);
        System.out.println("ThreadLocalRandom int in range 0-9: " + randomIntRange);

        double randomDouble = ThreadLocalRandom.current().nextDouble();
        System.out.println("ThreadLocalRandom Double: " + randomDouble);
    }

    // java 8
    // thread safe no > multi threaded
    // high performance
    private static void randomSample03(){
        SplittableRandom splittableRandom = new SplittableRandom();

        int randomInt = splittableRandom.nextInt();
        System.out.println("SplittableRandom int: " + randomInt);

        int randomIntRange = splittableRandom.nextInt(10);
        System.out.println("SplittableRandom int in range 0-9: " + randomIntRange);

        double randomDouble = splittableRandom.nextDouble();
        System.out.println("SplittableRandom Double: " + randomDouble);
    }

    // java 1.1
    // thread safe
    // slow
    private static void randomSample02(){
        SecureRandom secureRandom = new SecureRandom();

        int randomInt = secureRandom.nextInt();
        System.out.println("Secure Random Int: " + randomInt);

        int randomIntRange = secureRandom.nextInt(10);
        System.out.println("Secure Random Int in range 0-9: " + randomIntRange);

        double randomDouble = secureRandom.nextDouble();
        System.out.println("Secure random double: " + randomDouble);
    }

    //java 1
    // no thread safe
    private static void randomSample01(){
        Random random = new Random();

        // Generate a random integer
        int randomInt = random.nextInt();
        System.out.println("Random Integer: " + randomInt);

        int randomIntRange = random.nextInt(10);
        System.out.println("Random Integer in range 0-9: " + randomIntRange);

        double randomDouble = random.nextDouble();
        System.out.println("Random Double: " + randomDouble);
    }




}
