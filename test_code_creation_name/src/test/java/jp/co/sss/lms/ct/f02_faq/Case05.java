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
 * ケース05
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース05 キーワード検索 正常系")
public class Case05 {

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

		// 2. ログイン画面であることの検証
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

	@Test
	@Order(5)
	@DisplayName("テスト05 キーワード検索で該当キーワードを含む検索結果だけ表示")
	void test05() {
		// 1. キーワード入力欄に「研修」を入力
		webDriver.findElement(By.name("keyword")).sendKeys("研修");

		// 2. 「検索」ボタンを押下
		webDriver.findElement(By.xpath("//input[@value='検索']")).click();

		// 3. 検索結果画面に「研修」が含まれることを検証
		assertTrue(webDriver.findElement(By.tagName("body")).getText().contains("研修"));

		// 4. エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 「クリア」ボタン押下で入力したキーワードを消去")
	void test06() {
		// 1. 「クリア」ボタンを押下s
		webDriver.findElement(By.xpath("//input[@value='クリア']")).click();

		// 2. 入力欄の値が空（""）になっていることを検証
		String inputValue = webDriver.findElement(By.name("keyword")).getAttribute("value");
		assertEquals("", inputValue);

		// 3. エビデンス取得
		getEvidence(new Object() {
		});
	}

}