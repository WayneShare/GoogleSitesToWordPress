package com.wayneshare.utils;

import java.net.MalformedURLException;
import java.util.List;

import org.apache.commons.cli.Options;

import net.bican.wordpress.FilterPost;
import net.bican.wordpress.Post;
import net.bican.wordpress.Wordpress;
import net.bican.wordpress.configuration.WpCliConfiguration;
import net.bican.wordpress.exceptions.InsufficientRightsException;
import net.bican.wordpress.exceptions.InvalidArgumentsException;
import net.bican.wordpress.exceptions.ObjectNotFoundException;
import redstone.xmlrpc.XmlRpcFault;

public class WordPressUtil {

	public static void printAllPostsPages(final Wordpress wp)
			throws MalformedURLException, XmlRpcFault,
			InsufficientRightsException, InvalidArgumentsException,
			ObjectNotFoundException {

		// final FilterPost filter = new FilterPost();
		// filter.setNumber(10);
		final List<Post> recentPosts = wp.getPosts();
		System.out.println("Here are the recent posts:");
		for (final Post page : recentPosts) {
			System.out.println(page.getPost_id() + ":" + page.getPost_title());
		}

		final FilterPost filter2 = new FilterPost();
		filter2.setPost_type("page");
		wp.getPosts(filter2);
		final List<Post> pages = wp.getPosts(filter2);
		System.out.println("Here are the pages:");
		for (final Post pageDefinition : pages) {
			System.out.println(pageDefinition.getPost_title());
		}

	}

	public static List<Post> findAllPosts(final Wordpress wp)
			throws MalformedURLException, XmlRpcFault,
			InsufficientRightsException, InvalidArgumentsException,
			ObjectNotFoundException {

		// final FilterPost filter = new FilterPost();
		// filter.setNumber(10);
		final List<Post> recentPosts = wp.getPosts();
		System.out.println("Here are the recent posts:");
		for (final Post page : recentPosts) {
			System.out.println(page.getPost_id() + ":" + page.getPost_title());
		}

		return recentPosts;
	}

	public static void createNewPost(Post recentPost, Wordpress wp)
			throws InsufficientRightsException, InvalidArgumentsException,
			ObjectNotFoundException, XmlRpcFault {
		System.out.println("Posting a test (draft) page...");
		final Integer result = wp.newPost(recentPost);
		System.out.println("new post page id: " + result);
	}

	public static void deleteAllPosts(List<Post> recentPosts, final Wordpress wp)
			throws XmlRpcFault, InsufficientRightsException,
			ObjectNotFoundException {
		for (Post oneP : recentPosts) {
			deletePostbyID(oneP.getPost_id(), wp);
		}
	}

	public static void deletePostbyID(Integer post_ID, final Wordpress wp)
			throws XmlRpcFault, InsufficientRightsException,
			ObjectNotFoundException {
		if (post_ID != null) {
			System.out.println(wp.deletePost(post_ID));
		}
	}

}