
public class Main {
    public static void main(String[] args) {
        //instanceofSample();
        //switchSampleV4("WEDNESDAY");
        //switchSampleV4(14);
        //switchSampleV4(null);
        //switchSampleV4(1.10);
        stringSampleV9();
    }

    //15+
    public static void stringSampleV9(){
        String str = "Java\nis\nawesome";
        str.lines().forEach(System.out::println);
    }

    //15+
    public static void stringSampleV8(){
        String str = "Hi".repeat(3);
        System.out.println(str);
    }

    //15+
    public static void stringSampleV7(){
        String str = "    ";
        System.out.println(str.isBlank());
    }

    //15+
    public static void stringSampleV6(){
        String str = "    Hello Java.   ";
        System.out.println(str.strip());
    }

    //21+
    public static void stringSampleV5(){
        String name = "Danial";
        int age = 40;

        String greeting = STR."""
        V5: Hello \{name}!
        You are \{age} years old.
        """;

        System.out.println(greeting);
    }

    //21+
    public static void stringSampleV4(){
        String name = "Danial";
        int age = 40;

        String greeting = STR."V4: Hello \{name}! \n You are \{age} years old.";

        System.out.println(greeting);
    }

    public static void stringSampleV3(){
        String name = "Danial";
        int age = 40;

        String greeting = String.format("Hello %s! \n You are %d years old.", name, age);

        System.out.println(greeting);
    }

    //13+
    public static void stringSampleV2(){
        String name = "Danial";
        int age = 40;

        String greeting = """
                Hello Danial!
                You are 40 years old.
                """;
        System.out.println(greeting);
    }

    public static void stringSampleV1(){
        String name = "Danial";
        int age = 40;

        String greeting = "Hello " + name + "! \nYou are " + age + " years old.";
        System.out.println(greeting);
    }


    //17+
    public static void switchSampleV4(Object obj){

        String result = switch (obj){
            case String s -> String.valueOf(s.length());
            case Integer i -> {
                System.out.println("Test");
                yield "Integer: " + i;
            }
            case null -> "It's null!";
            default -> "default";
        };

        System.out.println(result);
    }

    // 14+
    public static void switchSampleV3(String day){

        int numLetters = switch (day){
            case "MONDAY", "FRIDAY", "SUNDAY" -> 6;
            case "TUESDAY" -> 7;
            case "THURSDAY", "SATURDAY" -> 8;
            case "WEDNESDAY" -> {
                System.out.println("Wednesday is a mid-week day.");
                yield 9;
            }
            default -> 0;
        };

        System.out.println("Number of letters in " + day + ": " + numLetters);
    }

    public static void switchSampleV2(String day){
        int numLetters;

        switch (day){
            case "MONDAY":
            case "FRIDAY":
            case "SUNDAY":
                numLetters = 6;
                break;
            case "TUESDAY":
                numLetters = 7;
                break;
            case "THURSDAY":
            case "SATURDAY":
                numLetters = 8;
                break;
            case "WEDNESDAY":
                numLetters = 9;
                break;
            default:
                numLetters = 0;
        }

        System.out.println("Number of letters in " + day + ": " + numLetters);
    }

    public static void switchSampleV1(String day){
        int numLetters;
        if(day == "SUNDAY"){
            numLetters = 6;
        } else if (day == "TUESDAY"){
            numLetters = 7;
        } else {
            numLetters = 0;
        }

        System.out.println("Number of letters in " + day + ": " + numLetters);
    }

    public static void instanceofSample(){
//        Object obj = "Hello, Java!";
//        if(obj instanceof String){
//            String str = (String) obj;
//            System.out.println(str.length());
//        }

        //16+
        Object obj = "Hello, Java!";
        if(obj instanceof String str){
            System.out.println(str.length());
        }
    }
}
