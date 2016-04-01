package com.wayneshare;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.bican.wordpress.Post;
import net.bican.wordpress.Wordpress;
import net.bican.wordpress.exceptions.InsufficientRightsException;
import net.bican.wordpress.exceptions.InvalidArgumentsException;
import net.bican.wordpress.exceptions.ObjectNotFoundException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redstone.xmlrpc.XmlRpcFault;

import com.wayneshare.common.Config;
import com.wayneshare.utils.ConfigUtils;
import com.wayneshare.utils.DateTimeUtils;
import com.wayneshare.utils.FileFolderUtils;
import com.wayneshare.utils.WordPressUtil;

public class GoogleToWordPress {

	public static final Logger LOG = LoggerFactory
			.getLogger(GoogleToWordPress.class);

	public static void main(String[] args) throws IOException {
		// validate args
		// CommandLine line = ArgsProcessor.processArgs(args);

		// simulate a user input args
		Config inputConfig = ConfigUtils.getConfig();
		// /////////////////////////////////////////////////////////////
		// /////////////////////////////////////////////////////////////
		// /////////////////////////////////////////////////////////////
		List<Post> posts = createPostsFromIndexFiles(inputConfig.googleSitesExportFolder);

		// write posts to wordpress
		Wordpress wp = new Wordpress(inputConfig.username,
				inputConfig.password, inputConfig.wordpressURL);
		for (Post p : posts) {
			try {
				// delete all post //
				List<Post> allPosts = WordPressUtil.findAllPosts(wp);
				// WordPressUtil.deleteAllPosts(allPosts, wp);

				// create one post
				// WordPressUtil.createNewPost(p, wp);
			} catch (InsufficientRightsException e) {
				e.printStackTrace();
			} catch (InvalidArgumentsException e) {
				e.printStackTrace();
			} catch (ObjectNotFoundException e) {
				e.printStackTrace();
			} catch (XmlRpcFault e) {
				e.printStackTrace();
			}

		}

		LOG.debug("===============================================================");
		LOG.debug("================ Task finished               ==================");
		LOG.debug("================ Task finished               ==================");
		LOG.debug("===============================================================");

		// and also can do others
		System.exit(0);
		return;
	}

	public static List<Post> createPostsFromIndexFiles(String singleFolderName) {

		List<Post> rtList = new ArrayList<Post>();

		String singleFUllName = singleFolderName;

		if (StringUtils.isNotBlank(singleFolderName)) {
			if (FileFolderUtils.isFolderExisting(singleFUllName)) {
				// temp = singleFUllName;
			} else {
				LOG.error("*********************************");
				LOG.error("ERROR: Folder does not exist - " + singleFUllName);
				return null;
			}
		}

		List<String> resultAllList = new ArrayList<String>();
		File folder = new File(singleFUllName);
		String folder1 = folder.getAbsolutePath();
		List<File> allFiles = FileFolderUtils
				.findAllFilesByExtensionUnderFolder(folder,
						new String[] { "html" }, true);
		if (CollectionUtils.isNotEmpty(allFiles)) {
			for (File logFile : allFiles) {
				if (logFile.getName().equalsIgnoreCase("index.html")) {

					// /////////////////////////////////////////////////////
					// 1. read all index.html as the html node obj
					Document doc = null;
					try {
						doc = Jsoup.parse(logFile, "UTF-8",
								"https://sites.google.com/site/wzhsite/");
					} catch (IOException e) {
						e.printStackTrace();
					}

					// /////////////////////////////////////////////////////
					// LOG.debug(" title is  ===============");
					Elements links = doc.getElementsByTag("title");
					for (Element link : links) {
						String linkText = link.text();
						// LOG.debug(linkText);
					}

					// LOG.debug("entry-title title is  ===============");
					Elements entry_title = doc
							.getElementsByClass("entry-title");
					for (Element title : entry_title) {
						// LOG.debug(title.text());
					}

					// LOG.debug(" Update date is  ===============");
					Elements updatesdate = doc.getElementsByTag("abbr");
					for (Element link : updatesdate) {
						String linkText = link.text();
						// LOG.debug(linkText);
					}

					LOG.debug("entry-content is  ===============");
					Elements entry_content = doc
							.getElementsByClass("entry-content");
					String title = (String) (entry_title.get(0).text());
					Date updateDate = DateTimeUtils
							.getDate_MMddYYYY(updatesdate.get(0).text());
					LOG.debug(title + " ** " + updateDate);
					if (entry_content != null) {
						for (Element title11 : entry_content) {
							// LOG.debug(title11.text());
							// LOG.debug(title.html());
							// LOG.debug(title.data());
						}
						// ////////////////////////////////////////////////////////
						// ///////////////////////////////////////////////////

						String content = entry_content.get(0).html();
						String contentText = entry_content.get(0).text();

						if (StringUtils.isNotBlank(content)) {
							// 2. convert this node into a wordpress post.
							Post post = new Post();
							post.setPost_title(title);
							post.setPost_name(title);
							post.setPost_content(content);
							// post.setPost_status("draft");
							post.setPost_modified(updateDate);
							post.setPost_date(updateDate);
							if (contentText.length() > 50) {
								post.setPost_status("publish");
							}
							rtList.add(post);

						} else {
							LOG.error("empty  Content or very short content ****************");
							LOG.debug(content);
						}
					} else {
						LOG.error("NUll Content ****************");
					}

				}
			}
		}

		return rtList;
	}
}
