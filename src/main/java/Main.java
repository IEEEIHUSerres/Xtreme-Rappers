import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;

public class Main {

    private static Scanner getStandardScanner() {
        return new Scanner(System.in);
    }

    public static void main(final String... args) {
        Optional.of(getStandardScanner())
                .map(scanner -> Optional
                        .of(new XtremeRappersEngine())
                        .map(xtremeRappersEngine -> xtremeRappersEngine.run(
                                scanner.nextLong(),
                                scanner.nextLong()
                        )))
                .map(pairsOptional -> pairsOptional.orElse(0L))
                .map(pairs -> String.format("%s", pairs))
                .map(pairs -> {
                    System.out.println(pairs);
                    return pairs;
                });
    }

    public static class Rapper {

        private String name;
        private Long words;

        public Rapper(final String name,
                      final Long words) {
            this.name = name;
            this.words = words;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Long getWords() {
            return words;
        }

        public void setWords(Long words) {
            this.words = words;
        }

        public void minusWords(Long wordsToMinus) {
            words -= wordsToMinus;
        }
    }

    public static abstract class Validator {
        public static Boolean validWordPair(final Rapper firstRapper,
                                            final Rapper secondRapper) {
            if (Objects.isNull(firstRapper)) {
                return false;
            }

            if (Objects.isNull(secondRapper)) {
                return false;
            }

            if (firstRapper.getWords() == 0L) {
                return false;
            }

            if (secondRapper.getWords() == 0L) {
                return false;
            }

            if ((firstRapper.getWords() + secondRapper.getWords()) < 3L) {
                return false;
            }

            return true;
        }
    }

    public static class XtremeRappersEngine {

        private final Rapper firstRapper;
        private final Rapper secondRapper;

        public XtremeRappersEngine() {
            firstRapper = new Rapper("firstRapper", 0L);
            secondRapper = new Rapper("secondRapper", 0L);
        }

        public Rapper getRapperForOneContribution() {
            Long firstRapperWords = firstRapper.getWords();
            if (firstRapperWords == 1L) {
                return firstRapper;
            }

            Long secondRapperWords = secondRapper.getWords();
            if (secondRapperWords == 1L) {
                return secondRapper;
            }

            return (firstRapperWords.equals(secondRapperWords))
                    ? firstRapper
                    : (firstRapperWords < secondRapperWords)
                    ? firstRapper
                    : secondRapper;
        }

        public Boolean isDiffByOne() {
            return (firstRapper.getWords() - 1 == secondRapper.getWords());
        }

        public Rapper getWithMostWords() {
            return firstRapper.getWords() > secondRapper.getWords()
                    ? firstRapper
                    : secondRapper;
        }

        public Long threeTimes() {
            return Arrays.asList(
                    firstRapper,
                    secondRapper
            ).parallelStream()
                    .map(Rapper::getWords)
                    .map(aLong -> aLong / 3)
                    .min(Long::compareTo)
                    .orElse(0L);
        }

        public Long run(final Long firstRapperWords,
                        final Long secondRapperWords) {
            firstRapper.setWords(firstRapperWords);
            secondRapper.setWords(secondRapperWords);

            Long result = 0L;

            if (!(Validator.validWordPair(firstRapper, secondRapper))) {
                return result;
            }

            if (isDiffByOne()) {
                Rapper withMostWords = getWithMostWords();
                Long withMostWordsWords = withMostWords.getWords();

                Double v = (withMostWordsWords / 2) * 1.0;

                v += 0.01;

                return v.longValue();
            }

            Long aLong = threeTimes();

            result += (aLong * 2L);

            firstRapper.minusWords(aLong * 3L);
            secondRapper.minusWords(aLong * 3L);

            if (!(Validator.validWordPair(firstRapper, secondRapper))) {
                return result;
            }

            while (Validator.validWordPair(firstRapper, secondRapper)) {
                Rapper rapperForOneContribution = getRapperForOneContribution();

                rapperForOneContribution.minusWords(1L);

                if (rapperForOneContribution.equals(firstRapper)) {
                    secondRapper.minusWords(2L);
                } else {
                    firstRapper.minusWords(2L);
                }

                result++;
            }

            return result;
        }

    }
}
