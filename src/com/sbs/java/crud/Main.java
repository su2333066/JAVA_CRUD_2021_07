package com.sbs.java.crud;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		System.out.println("== 프로그램 시작 ==");

		Scanner sc = new Scanner(System.in);

		int lastArticleId = 0;

		List<Article> articles = new ArrayList<>();

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
			if (command.equals("article write")) {
				int id = lastArticleId + 1;
				lastArticleId = id;
				System.out.printf("제목 : ");
				String title = sc.nextLine();
				System.out.printf("내용 : ");
				String body = sc.nextLine();
				
				Article article = new Article(id, title, body);
				article.now = new Date();
				articles.add(article);

				System.out.printf("%d번글이 생성되었습니다.\n", id);

			} else if (command.equals("article list")) {
				if(articles.size() == 0) {
					System.out.println("게시물이 없습니다.");
					continue;
				}
				
				for(int i = articles.size()-1; i >= 0; i--) {
					Article article = articles.get(i);
					System.out.printf("%d   |   %s\n", article.id, article.title);
				}
			} else if(command.startsWith("article detail ")) {
				String[] commandBits = command.split(" ");
				
				int id = Integer.parseInt(commandBits[2]);
				Article foundArticle = null;
				
				for(int i = 0; i < articles.size(); i++) {
					Article article = articles.get(i);
					
					if(article.id == id) {
						foundArticle = article;
						break;
					}
				}
				
				if(foundArticle == null) {
					System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
					continue;
				}
				else {
					System.out.printf("%d번 게시물은 존재합니다.\n", id);
					System.out.println("번호: " + foundArticle.id);
					System.out.println("날짜: " + foundArticle.now);
					System.out.println("제목: " + foundArticle.title);
					System.out.println("내용: " + foundArticle.body);
					
					continue;
				}
				
				
			} else if(command.startsWith("article delete ")) {
				String[] commandBits = command.split(" ");
				
				int id = Integer.parseInt(commandBits[2]);
				Article foundArticle = null;
				
				for(int i = 0; i < articles.size(); i++) {
					Article article = articles.get(i);
					
					if(article.id == id) {
						foundArticle = article;
						break;
					}
				}
				
				if(foundArticle == null) {
					System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
					continue;
				}
				
				articles.remove(articles.indexOf(foundArticle));
				System.out.printf("%d번 게시물울 지웠습니다.\n", id);
				
			}
			else {
				System.out.printf("%s(은)는 존재하지 않는 명령어 입니다.\n", command);
			}
		}

		sc.close();

		System.out.println("== 프로그램 끝 ==");
	}
}

class Article {
	int id;
	String title;
	String body;
	Date now;
	

	public Article(int id, String title, String body) {
		this.id = id;
		this.title = title;
		this.body = body;
	}
}