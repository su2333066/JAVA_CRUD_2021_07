package com.sbs.java.crud;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.sbs.java.crud.controller.ArticleController;
import com.sbs.java.crud.controller.MemberController;
import com.sbs.java.crud.dto.Article;
import com.sbs.java.crud.dto.Member;
import com.sbs.java.crud.util.Util;

public class App {

	private List<Article> articles;
	private List<Member> members;

	public App() {
		articles = new ArrayList<>();
		members = new ArrayList<>();
	}

	public void start() {
		System.out.println("== 프로그램 시작 ==");

		makeTestData();

		Scanner sc = new Scanner(System.in);
		
		MemberController memberController = new MemberController(sc, members);
		ArticleController articleController = new ArticleController();
		
		

		while (true) {
			System.out.printf("명령어) ");
			String command = sc.nextLine();

			command = command.trim();

			if (command.length() == 0) {
				continue;
			}

			if (command.equals("system exit")) {
				break;
			}
			if (command.equals("member join")) {
				memberController.doJoin();
				
				

			} else if (command.equals("article write")) {
				int id = articles.size() + 1;
				String regDate = Util.getNowDateStr();
				System.out.printf("제목 : ");
				String title = sc.nextLine();
				System.out.printf("내용 : ");
				String body = sc.nextLine();

				Article article = new Article(id, regDate, title, body, 0);
				articles.add(article);

				System.out.printf("%d번 글이 생성되었습니다.\n", id);

			} else if (command.startsWith("article list")) {

				String searchKeyword = command.substring("article list".length()).trim();

				List<Article> forListArticles = articles;

				if (searchKeyword.length() > 0) {
					forListArticles = new ArrayList<>();

					for (Article article : articles) {
						if (article.title.contains(searchKeyword)) {
							forListArticles.add(article);
						}
					}

					if (forListArticles.size() == 0) {
						System.out.println("검색결과가 존재하지 않습니다.");
						continue;
					}
				}

				if (articles.size() == 0) {
					System.out.println("게시글이 없습니다.");
					continue;
				}

				System.out.print("번호 | 조회| 제목\n");

				for (int i = forListArticles.size() - 1; i >= 0; i--) {
					Article article = forListArticles.get(i);

					System.out.printf("%4d|%4d|%s\n", article.id, article.hit, article.title);
				}
			} else if (command.startsWith("article detail ")) {
				String[] commandBits = command.split(" ");

				int id = Integer.parseInt(commandBits[2]);

				Article foundArticle = getArticleById(id);

				if (foundArticle == null) {
					System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
					continue;
				}

				foundArticle.increaseHit();

				System.out.printf("번호 : %d\n", foundArticle.id);
				System.out.printf("날짜 : %s\n", foundArticle.regDate);
				System.out.printf("제목 : %s\n", foundArticle.title);
				System.out.printf("내용 : %s\n", foundArticle.body);
				System.out.printf("조회수 : %d\n", foundArticle.hit);

			} else if (command.startsWith("article modify ")) {
				String[] commandBits = command.split(" ");

				int id = Integer.parseInt(commandBits[2]);

				Article foundArticle = getArticleById(id);

				if (foundArticle == null) {
					System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
					continue;
				}

				System.out.printf("제목 : ");
				String title = sc.nextLine();
				System.out.printf("내용 : ");
				String body = sc.nextLine();

				foundArticle.title = title;
				foundArticle.body = body;

				System.out.printf("%d번글이 수정되었습니다.\n", id);

			} else if (command.startsWith("article delete ")) {
				String[] commandBits = command.split(" ");

				int id = Integer.parseInt(commandBits[2]);

				int foundIndex = getArticleIndexbyId(id);

				if (foundIndex == -1) {
					System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
					continue;
				}
				articles.remove(foundIndex);
				System.out.printf("%d번 게시물이 삭제되었습니다.\n", id);
			}

			else {
				System.out.printf("%s(은)는 존재하지 않는 명령어 입니다.\n", command);
			}
		}

		sc.close();

		System.out.println("== 프로그램 끝 ==");
	}



	private int getArticleIndexbyId(int id) {
		int i = 0;

		for (Article article : articles) {

			if (article.id == id) {
				return i;
			}
			i++;
		}

		return -1;
	}

	private Article getArticleById(int id) {

		int index = getArticleIndexbyId(id);

		if (index != -1) {
			return articles.get(index);
		}

		return null;
	}

	private void makeTestData() {
		System.out.println("테스트를 위한 데이터를 생성합니다.");

		articles.add(new Article(1, Util.getNowDateStr(), "제목1", "내용1", 11));
		articles.add(new Article(2, Util.getNowDateStr(), "제목2", "내용2", 22));
		articles.add(new Article(3, Util.getNowDateStr(), "제목3", "내용3", 33));

	}

}