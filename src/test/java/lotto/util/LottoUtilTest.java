package lotto.util;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LottoUtilTest {
    @Test
    void 구매_장수를_계산한다() {
        int 구매금액 = 14000;
        int 구매장수 = LottoUtil.calculateLottoQuantity(구매금액);
        assertThat(구매장수).isEqualTo(14);
    }

    @Test
    void 당첨번호_문자를_숫자리스트로_변환한다() {
        String input = "1, 2, 3, 4, 5, 6";
        List<Integer> winningNumbers = LottoUtil.convertWinningNumber(input);

        assertThat(winningNumbers).size().isEqualTo(6);
        assertThat(winningNumbers).containsExactly(1, 2, 3, 4, 5, 6);
    }

    @Test
    void 당첨번호_입력값을_검증한다() {
        String[] winNumbers = new String[]{"1", "2", "3", "4", "5", "5"};

        assertThatThrownBy(()-> LottoUtil.checkWinNumberValidation(winNumbers))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("당첨 번호는 6개가 입력 되어야 합니다.(중복 제외)");
    }
}
