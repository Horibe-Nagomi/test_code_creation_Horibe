package jp.co.sss.lms.ct.f02_faq;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;

/**
 * 結合テスト よくある質問機能
 * ケース04
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース04 よくある質問画面への遷移")
public class Case04 {

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
		// 1. トップページへアクセス
		goTo("http://localhost:8080/lms");

		// 2. ログイン画面であることの検証（タイトル比較）
		assertEquals("ログイン | LMS", webDriver.getTitle());

		// 3. エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {
		// 1. 正常なログインIDとパスワードを入力
		webDriver.findElement(By.id("loginId")).sendKeys("StudentAA03");
		webDriver.findElement(By.id("password")).sendKeys("Nagomi0715");

		// 2. ログインボタンを押下
		webDriver.findElement(By.xpath("//input[@type='submit']")).click();

		// 3. 遷移後の画面に「コース詳細」「DEMOコース」と表示されていることを検証
		String actualText = webDriver.findElement(By.tagName("body")).getText();
		assertTrue(actualText.contains("コース詳細"));
		assertTrue(actualText.contains("DEMOコース"));

		// 4. エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() {
		// 1. 「機能」ボタン（メニュー）を押下
		webDriver.findElement(By.linkText("機能")).click();

		// 2. プルダウン内の「ヘルプ」を選択
		webDriver.findElement(By.linkText("ヘルプ")).click();

		// 3. ヘルプ画面に遷移し、タイトルまたは画面内に「ヘルプ」が含まれることを検証
		String actualText = webDriver.findElement(By.tagName("body")).getText();
		assertTrue(actualText.contains("ヘルプ"));

		// 4. エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() {
		// 1. 「よくある質問」を押下
		webDriver.findElement(By.linkText("よくある質問")).click();

		// 2. 新しいタブに切り替え
		Object[] windowHandles = webDriver.getWindowHandles().toArray();
		if (windowHandles.length > 1) {
			webDriver.switchTo().window(windowHandles[1].toString());
		}

		// 3. 画面に「よくある質問」と「キーワード検索」が表示されていることを検証
		String actualText = webDriver.findElement(By.tagName("body")).getText();
		assertTrue(actualText.contains("よくある質問"));
		assertTrue(actualText.contains("キーワード検索"));

		// 4. エビデンス取得
		getEvidence(new Object() {
		});

	}

}