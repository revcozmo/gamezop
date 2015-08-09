from time import sleep
import time
import tweepy
import os
from httplib import HTTPConnection
import datetime
consumer_key = "QnhISpEWECE3ML4kdkmcHb10z"
consumer_secret = "5OGSamv7COmKVsF1M1JlZA4gEWiP4L7iuBVO4D8G2blhBTLUnT"
access_token = "60095625-NpDcS6yCok3QmKzXWtWVcY4w8jl5sNfxZilhQPZDH"
access_token_secret = "qxgjWcJD4bJHv2mSZHLTK3PTqCxtFbBAp6jYcU4EA1ljb"

auth = tweepy.OAuthHandler(consumer_key, consumer_secret)
auth.set_access_token(access_token, access_token_secret)
proxies = {"http":"http://proxy.62.iitd.ernet.in:3128","https":"https://proxy62.iitd.ernet.in:3128"}
#HTTPConnection.set_tunnel("http://proxy62.iitd.ernet.in", port="3128", headers=None)
api = tweepy.API(auth,proxy="http:proxy62.iitd.ernet.in:3128")
#HTTPConnection.set_tunnel("http://proxy62.iitd.ernet.in", port="3128", headers=None)
ids = []

with open('usernames.txt') as f:
    usernames = f.readlines()
print usernames

for i in range(0,len(usernames)):
    
    bakchod = usernames[i]
    a = True
    if not os.path.isfile(bakchod[:-1]+"_following.txt"):
        print bakchod[:-1]
        users = tweepy.Cursor(api.friends, screen_name=bakchod[:-1]).items()
        text_file = open(bakchod[:-1]+"_following.txt", "w")
        b = True
        while b == True: 
            try:
                user = next(users)
                text_file.write(user.screen_name+"\n")
                sleep(0.2)
            except tweepy.TweepError,e:
                print
                print "Time = ",datetime.datetime.now().strftime('%H:%M:%S')
                print
                if e == "[{u'message': u'Rate limit exceeded', u'code': 88}]":
                    sleep(60*7)
                    print "Time = ",datetime.datetime.now().strftime('%H:%M:%S')
                else:
                    sleep(60*7)
                while a==True:
                    try:
                        user = next(users)
                        text_file.write(user.screen_name+"\n")
                        sleep(0.2)
                        #text_file.write(user.screen_name+"\n")
                        #print user.screen_name
                        a = False
                    except tweepy.TweepError,e:
                        if e == "[{u'message': u'Rate limit exceeded', u'code': 88}]":
                            sleep(60*2)
                            print "Time = ",datetime.datetime.now().strftime('%H:%M:%S')
                        else:
                            sleep(60*2)
                            print "Time = ",datetime.datetime.now().strftime('%H:%M:%S')
                #user = next(users)
            except StopIteration, e:
                if e == "[{u'message': u'Rate limit exceeded', u'code': 88}]":
                    sleep(60*2)
                    print "Time = ",datetime.datetime.now().strftime('%H:%M:%S')
                else:
                    text_file.close()
                    b = False
                    #time.sleep(60*2)
                    #break
            #text_file.write(user.screen_name+"\n")
            print str(user.screen_name)
        text_file.close()
"""for page in tweepy.Cursor(api.friends_ids, screen_name="agarwalyashash").pages():
ids.extend(page)
time.sleep(100)
screen_names = [user.screen_name for user in api.lookup_users(user_ids=ids)]
for name in screen_names:
name = str(name)
print name"""
