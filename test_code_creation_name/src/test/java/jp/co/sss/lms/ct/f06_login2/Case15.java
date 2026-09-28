package jp.co.sss.lms.ct.f06_login2;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;

/**
 * 結合テスト ログイン機能②
 * ケース15
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース15 受講生 初回ログイン 利用規約に不同意")
public class Case15 {

	/** 前処理 */
	@BeforeAll
	static void before() {
		createDriver();
	}

	/** 後処理 */
	@AfterAll
	static void after() {
		closeDriver();
	}

	@Test
	@Order(1)
	@DisplayName("テスト01 トップページURLでアクセス")
	void test01() {
		// 1. ログイン画面sへアクセス
		goTo("http://localhost:8080/lms/");

		// 2. ログイン画面であることの検証
		assertEquals("ログイン | LMS", webDriver.getTitle());

		// 3. エビデンスを取得して保存
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 DBに初期登録された未ログインの受講生ユーザーでログイン")
	void test02() {

		// 1. 未ログインのログインIDとパスワードを入力
		webDriver.findElement(By.id("loginId")).sendKeys("StudentAA05");
		webDriver.findElement(By.id("password")).sendKeys("StudentAA05");

		// 2. ログインボタンを押下
		webDriver.findElement(By.xpath("//input[@type='submit']")).click();

		// 3. 遷移後の画面に「利用規約」と表示されていることを検証
		String actualText = webDriver.findElement(By.tagName("body")).getText();
		assertTrue(actualText.contains("利用規約"));

		// 4. エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 「同意します」チェックボックスにチェックをせず「次へ」ボタンを押下")
	void test03() {
		//1.次へボタンを押下
		webDriver.findElement(By.xpath("//input[@value='次へ'] | //button[text()='次へ']")).click();

		//2.遷移後の画面に「セキュリティ規約への同意は必須です。」というエラーメッセージが表示される。
		String actualText = webDriver.findElement(By.tagName("body")).getText();
		assertTrue(actualText.contains("セキュリティ規約への同意は必須です。"));

		// 3. エビデンス取得
		getEvidence(new Object() {
		});
	}

}
