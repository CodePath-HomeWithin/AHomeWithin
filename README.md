# AHomeWithin

CodePath.com Android Team Project, Winter 2016


# Team Members and email:
Xiangyang Xiao <ilikedeal@gmail.com>
Jose Luis Martín <joseluismartin@gmail.com>
Barbara Raitz <braitz@gmail.com>

# Project Description and Proposed Features:
A Home Within (ahomewithin.org) is a national organization dedicated to ensuring the emotional well being of foster children.  This organization has a website, but not a mobile application.  Our goal for the project is to build an android application that could help this meaningful cause.

The target audience for our app includes: foster parents, case workers, educators, school counselors, child welfare advocates, and parents in general.  

The basic functionality of the app will include: 
*  general home, information page
*  login, user-profile flows
*  searching through products available for purchase
*  shopping cart and purchase experience
*  library of purchased/downloaded products.
*  view video
*  view conversation cards
*  find therapists and nearby parents on map
*  chat with people found on map
* search upcoming events near you
* view recommended books, blogs, and podcasts


## Demo

http://imgur.com/UoaGVOe

http://i.imgur.com/UoaGVOe.gif


<img src='http://i.imgur.com/UoaGVOe.gif?1' title='Video Walkthrough' width='' alt='Video Walkthrough' />

previous:  i.imgur.com/P798p6S.gif

##Walkthrough of user profile related functionalities, major part of which includes creating user, logging in, password reset and editting profile
<img src='https://www.dropbox.com/s/zd1rep40gn5ag7h/walkthrough.gif?dl=0' title='Video Walkthrough' width='' alt='Video Walkthrough' />


GIF created with [LiceCap](http://www.cockos.com/licecap/).


# TODO -- MILESTONES

Store/Library Stream Flow:
	- looks like both video and conversation cards are in the same list
	- Library page looks exactly like Store page -- should not show BUY
	- on library page, clicking on video should go to video detail player view
	- on library page, clicking on conversation should go to conversation card view
	- better, less cluttered images

User flow:
	- If you are not logged on, don't show profile in toolbar
	- If you are logged in, show profile image
	- clicking the profile in the drawer should go to user profile page
	- After login, there is no way to get to where we need to go.  Can we please integrate the drawer here for access?
	- After purchase, it would be IDEAL to go to DETAIL PAGE of library!!!
Home Page:
	- we should reorder for demo
		- tools and training
		- then library
		- near you
Maps:
	- either load users first, or stub users out in json for faster load
	- replace popup with dialog box and working button
	- add gestures to map

Events, LearnMore, Recommended:
	- I can own this...  Stub out a little more data and make it look good, perhaps combine into one page

About Us:
	- provide some content

Chat:
	- don't know status
	- hook up to map button (which doesn't exist yet)


STYLING!!!!!!:
	- font
	- colors
	- title
	- borders
	- icons in toolbar
	
styling:
	- fonts
	- colors, borders!
	- images in toolbar
	- icons for recommended


# Suggested Demo Flow

Demo Flow:
	- open app
	- explore top level Tools and Training
		- see videos and cards
		- purchase video click
			- create user
			- then back to purchase fragment?
			- purchase
				- go to library detailed view showing video
	- pull out drawer to get to Tools and Training
		- see videos and cards
		- purchase card click
			- purchase successful => go to library detail view showing conversation card.
	- pull out drawer to get back to home
		- quickly view my library
	- back to home
		- click near you
			- zoom to your location
			- click to view several people
			- initiate chat
			- chat a bit...
	- quickly visit Eventts
	- quickly visit Learn More
	- quickly visit About Us

	=> done


