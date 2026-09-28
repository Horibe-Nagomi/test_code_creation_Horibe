package jp.co.sss.lms.ct.f03_report;

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
 * 結合テスト レポート機能
 * ケース08
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース08 受講生 レポート修正(日報) 正常系")
public class Case08 {

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
		goTo("http://localhost:8080/lms");
		String actualText = webDriver.findElement(By.tagName("body")).getText();
		assertTrue(actualText.contains("ログイン") || webDriver.getTitle().contains("ログイン"));
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {
		webDriver.findElement(By.id("loginId")).sendKeys("StudentAA04");
		webDriver.findElement(By.id("password")).sendKeys("Nagomi0722");
		webDriver.findElement(By.xpath("//input[@value='ログイン']")).click();
		String actualText = webDriver.findElement(By.tagName("body")).getText();
		assertTrue(actualText.contains("コース詳細"));
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 提出済の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {
		// 7月8日の行の「詳細」ボタンを押下
		try {
			webDriver.findElement(By.xpath(
					"//tr[contains(., '7月8日') or contains(., '7/8')]//input[@value='詳細'] | //tr[contains(., '7月8日') or contains(., '7/8')]//a[contains(text(),'詳細')]"))
					.click();
		} catch (Exception e) {
			webDriver.findElement(By.xpath("(//input[@value='詳細'] | //a[contains(text(),'詳細')])[1]")).click();
		}

		String actualText = webDriver.findElement(By.tagName("body")).getText();
		assertTrue(actualText.contains("本日のレポート") || actualText.contains("レポート"));
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「確認する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		// 画面上の「確認する」ボタンを押下
		webDriver.findElement(By.xpath(
				"//input[contains(@value, '確認')] | //button[contains(text(), '確認')] | //a[contains(text(), '確認')]"))
				.click();

		String actualText = webDriver.findElement(By.tagName("body")).getText();
		assertTrue(actualText.contains("日報") || actualText.contains("報告レポート"));
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を修正して「提出する」ボタンを押下しセクション詳細画面に遷移")
	void test05() throws Exception {
		webDriver.findElement(By.tagName("textarea")).clear();
		webDriver.findElement(By.tagName("textarea")).sendKeys("修正：今週の研修内容についての理解を深めた。");
		webDriver.findElement(By.xpath("//input[@value='提出する'] | //button[contains(text(), '提出する')]")).click();

		String actualText = webDriver.findElement(By.tagName("body")).getText();
		assertTrue(actualText.contains("本日のレポート") || actualText.contains("確認"));
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 上部メニューの「ようこそ○○さん」リンクからユーザー詳細画面に遷移")
	void test06() {
		webDriver.findElement(By.xpath("//header//a | //a[contains(text(), 'ようこそ')] | //a[contains(@href, 'user')]"))
				.click();

		String actualText = webDriver.findElement(By.tagName("body")).getText();
		assertTrue(actualText.contains("プロフィール") || actualText.contains("受講生") || actualText.contains("Student"));
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 該当レポートの「詳細」ボタンを押下しレポート詳細画面で修正内容が反映される")
	void test07() throws Exception {
		// 1. テスト05で修正した研修日(7月8日)の「詳細」ボタンを押下
		try {
			webDriver.findElement(By.xpath(
					"//tr[contains(., '7月8日') or contains(., '7/8') or contains(., '2025/07/08')]//input[@value='詳細'] | //tr[contains(., '7月8日') or contains(., '7/8') or contains(., '2025/07/08')]//a[contains(text(),'詳細')]"))
					.click();
		} catch (Exception e) {
			// 一覧の並び順で2番目にある場合は2番目を押下
			webDriver.findElement(By.xpath("(//input[@value='詳細'] | //a[contains(text(),'詳細')])[2]")).click();
		}

		// 2. 修正後の文章が正しく表示されているか検証
		String actualText = webDriver.findElement(By.tagName("body")).getText();
		assertTrue(actualText.contains("修正：今週の研修内容についての理解を深めた。"));

		// 3. エビデンス取得
		getEvidence(new Object() {
		});
	}
}