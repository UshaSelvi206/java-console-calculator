import java.util.*;

class QuizQuestion {
private String question;
private List<String> options;
private int correctIndex;
public QuizQuestion(String question, List<String> options, int correctIndex) {
    if (options == null || options.size() < 2) {
        throw new IllegalArgumentException("Provide at least two options");
    }
    if (correctIndex < 0 || correctIndex >= options.size()) {
        throw new IllegalArgumentException("correctIndex out of range");
    }
    this.question = question;
    this.options = new ArrayList<>(options);
    this.correctIndex = correctIndex;
}

public String getQuestion() {
    return question;
}

public List<String> getOptions() {
    return new ArrayList<>(options);
}

public int getCorrectIndex() {
    return correctIndex;
}

public String getCorrectOption() {
    return options.get(correctIndex);
}

}

public class QuizApp {
public static void main(String[] args) {
Scanner scanner = new Scanner(System.in);
List<QuizQuestion> questions = loadQuestions();

    // Shuffle questions so each run is different
    Collections.shuffle(questions);

    int score = 0;
    int qNo = 1;

    for (QuizQuestion q : questions) {
        System.out.println("Q" + qNo + ". " + q.getQuestion());
        List<String> opts = q.getOptions();
        for (int i = 0; i < opts.size(); i++) {
            char label = (char) ('A' + i);
            System.out.println("  " + label + ". " + opts.get(i));
        }

        System.out.print("Your answer (A-" + (char)('A' + opts.size() - 1) + "): ");
        String line = scanner.nextLine().trim().toUpperCase();
        int answerIndex = -1;
        if (!line.isEmpty()) {
            char c = line.charAt(0);
            if (c >= 'A' && c < 'A' + opts.size()) {
                answerIndex = c - 'A';
            }
        }

        if (answerIndex == q.getCorrectIndex()) {
            System.out.println("Correct!\n");
            score++;
        } else {
            char correctLabel = (char) ('A' + q.getCorrectIndex());
            System.out.println("Wrong. Correct: " + correctLabel + ". " + q.getCorrectOption() + "\n");
        }

        qNo++;
    }

    int total = questions.size();
    double percent = ((double) score / total) * 100.0;
    System.out.printf("Quiz finished! Score: %d/%d (%.2f%%)%n", score, total, percent);

    scanner.close();
}

private static List<QuizQuestion> loadQuestions() {
    List<QuizQuestion> list = new ArrayList<>();

    list.add(new QuizQuestion(
            "What are Java loops?",
            Arrays.asList(
                    "A way to declare variables",
                    "Constructs to repeat code such as for, while, do-while",
                    "An exception type",
                    "A collection framework"
            ), 1));

    list.add(new QuizQuestion(
            "What is the enhanced for-loop in Java?",
            Arrays.asList(
                    "A for loop with index only (for i = 0; ...)",
                    "A loop that iterates directly over elements (for (Type x : collection))",
                    "A special while loop",
                    "A loop only for arrays"
            ), 1));

    list.add(new QuizQuestion(
            "How do you shuffle a list in Java?",
            Arrays.asList(
                    "Collections.shuffle(list)",
                    "list.shuffle()",
                    "Arrays.shuffle(list)",
                    "Sort with a random comparator"
            ), 0));

    list.add(new QuizQuestion(
            "What is an ArrayList?",
            Arrays.asList(
                    "A fixed-size array",
                    "A resizable array implementation in java.util",
                    "A 2D array",
                    "A thread only collection"
            ), 1));

    list.add(new QuizQuestion(
            "Which interface allows iterating over a collection explicitly?",
            Arrays.asList(
                    "Iterator",
                    "Iterable only",
                    "Comparable",
                    "Map.Entry"
            ), 0));

    return list;
}

}
