package lotto.domain.prize;

import lotto.domain.lotto.LottoNumber;
import lotto.domain.lotto.LottoNumbers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;

class WinningNumbersTest {

    @Test
    void 생성_테스트() {
        String enteredWinNumber = "1, 2, 3, 4, 5, 6";
        assertThatCode(() -> WinningNumbers.create(enteredWinNumber)).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @ValueSource(strings = {"1, 2, 3, 4, 5", "1, 2, 3, 4, 5, 6, 7"})
    void 당첨번호_입력수_확인(String enteredWinNumber) {
        assertThatThrownBy(() -> WinningNumbers.create(enteredWinNumber))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 당첨번호_중복입력_확인() {
        String enteredWinNumber = "1, 1, 2, 3, 4, 5";
        assertThatThrownBy(() -> WinningNumbers.create(enteredWinNumber))
                .isInstanceOf(IllegalArgumentException.class).hasMessage("중복된 당첨 번호가 있습니다");
    }

    @Test
    void 당첨번호_입력범위_확인() {
        String enteredWinNumber = "0, 1, 32, 37, 45, 49";
        assertThatThrownBy(() -> WinningNumbers.create(enteredWinNumber))
                .isInstanceOf(IllegalArgumentException.class).hasMessage("당첨 번호는 1 ~ 45 사이의 수 입니다");
    }

    @ParameterizedTest
    @CsvSource(value = {"2, 3, 4, 5, 9, 19 | 0", "1, 7, 23, 32, 33, 34 | 4"
            , "1, 7, 23, 34, 37, 44 | 6"}, delimiter = '|')
    void 당첨번호_일치개수를_확인한다(String enteredWinNumber, int expectedCount) {
        WinningNumbers winningNumbers = WinningNumbers.create(enteredWinNumber);
        List<LottoNumber> lottoNumber = Arrays.asList(1, 7, 23, 34, 37, 44).stream()
                .map(k -> LottoNumber.create(k)).collect(Collectors.toList());

        LottoNumbers lottoNumbers = LottoNumbers.create(lottoNumber);

        int matchCount = winningNumbers.findMatchCount(lottoNumbers);

        assertThat(matchCount).isEqualTo(expectedCount);
    }
}