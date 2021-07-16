package com.sbs.java.crud;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		System.out.println("===========프로그램 시작!!=============");
		Scanner sc = new Scanner(System.in);
		
		int LastArticleId = 0;
		List<Article> articles = new ArrayList<>();
		
		while (true) {

			System.out.printf("명령어: ");
			String command = sc.nextLine().trim();
			System.out.printf("입력된 명령어: %s\n", command);
			
			if(command.equals("system exit")) {
				break;
			}
			if(command.length() == 0) {
				continue;
			}
			if(command.equals("article write")) {
				int id = LastArticleId + 1;
				LastArticleId = id;
				System.out.printf("제목: ");
				String title = sc.nextLine();
				System.out.printf("내용: ");
				String body = sc.nextLine();
				articles.add(new Article(id, title, body));
				
				System.out.printf("%d번 글이 생성되었습니다.\n", id);
			}
			else if(command.equals("article list")) {
				for (int i = 0; i < articles.size(); i++) {
					Article article = articles.get(i);
					System.out.println(i+1 + "| " + "제목: " + article.title + " 내용: " + article.body);
				}
			}
			else {
				System.out.printf("%s는(은) 존재하지 않는 명령어 업니다.\n", command);
			}
		}
		
		sc.close();
		System.out.println("===========프로그램 끝=============");
	}

}

class Article{
	int id;
	String title;
	String body;
	
	public Article(int id, String title, String body){
		this.id = id;
		this.title = title;
		this.body = body;
	}
}