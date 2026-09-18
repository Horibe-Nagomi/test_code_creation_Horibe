package jp.co.sss.lms.ct.f01_login1;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*; // JUnit 5用に変更

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;

/**
 * 結合テスト ログイン機能①
 * ケース02
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース02 受講生 ログイン 認証失敗")
public class Case02 {

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
		// 1. ログイン画面へアクセス
		goTo("http://localhost:8080/lms/");

		// 2. ログイン画面であることの検証
		assertEquals("ログイン | LMS", webDriver.getTitle());

		// 3. エビデンスを取得して保存
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 DBに登録されていないユーザーでログイン")
	void test02() {
		// 1. 未登録のログインIDとパスワードを入力
		webDriver.findElement(By.id("loginId")).sendKeys("dummy_user");
		webDriver.findElement(By.id("password")).sendKeys("pass0722");

		// 2. ログインボタンを押下
		webDriver.findElement(By.xpath("//input[@type='submit']")).click();

		// 3. ログイン認証失敗画面であることの検証
		assertEquals("* ログインに失敗しました。", webDriver.findElement(By.className("error")).getText());

		// 4. エビデンス取得
		getEvidence(new Object() {
		});
	}
}