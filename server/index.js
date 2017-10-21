const express= require("express");
const mysql = require("mysql");
const jsonwebtoken=require("jsonwebtoken");
const bcrypt=require("bcrypt");
const bodyParser=require("body-parser");
const fcmPush=require("fcm-push");
const app= express();
const serverKey="AIzaSyAqI3OUH17gwwP0hjR5V27QnI9R3yIRFpA";




const db_config={
    host: "localhost",
    user: "root",
    password:"",
    database: "MobileComputing"
};

var connection;

function handleDisconnect() {
    connection = mysql.createConnection(db_config); // Recreate the connection, since
                                                    // the old one cannot be reused.
  
    connection.connect(function(err) {              // The server is either down
      if(err) {                                     // or restarting (takes a while sometimes).
        console.log('error when connecting to db:', err);
        setTimeout(handleDisconnect, 2000); // We introduce a delay before attempting to reconnect,
      }                                     // to avoid a hot loop, and to allow our node script to
    });                                     // process asynchronous requests in the meantime.
                                            // If you're also serving http, display a 503 error.
    connection.on('error', function(err) {
      console.log('db error', err);
      if(err.code === 'PROTOCOL_CONNECTION_LOST') { // Connection to the MySQL server is usually
        handleDisconnect();                         // lost due to either server restart, or a
      } else {                                      // connnection idle timeout (the wait_timeout
        throw err;                                  // server variable configures this)
      }
    });
  }
  
handleDisconnect();


//MiddleWare
//-----------------------------------------------------

app.use(function(req,res,next){
    if(req.headers && req.headers.authorization && req.headers.authorization.split(" ")[0]=="JWT"){
        jsonwebtoken.verify(req.headers.authorization.split(" ")[1],"Mobile123",function(err,decode){
            if(err){         
            } req.user=undefined;
            req.user=decode;
            console.log(req.user);
            next();
        });
    }else{
        req.user=undefined;
        next();
    }
})
app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());


app.get("/",function(req,res){
    res.send("Hello World");
})

app.get("/getBooks",function(req,res){
    let section= req.query.section;
    getBooksFromSection(section,(err,books)=>{
        if(err){
            console.log(err)
            res.json({err:true})
        }
        else{
            res.json(books)
        } 
    });
})

app.get("/getReviews",function(req,res){
    let book= req.query.book;
    getReviewsFromBook(book,(err,reviews)=>{
        if(err){
            console.log(err);
            res.json({err:true})
        } 
        else res.json(reviews);
    }); 
})

app.post("/postReview",function(req,res){
    console.log("hi")
    loginRequired(req,res,function(req,res){
        let review={
            rating:req.body.rating,
            reviewContent:req.body.review,
            book:req.body.book,
            language:req.body.language,
        }
        console.log("hi")
        postReview(review,(err,insertId)=>{
            if(err){
                console.log(JSON.stringify(err))
                res.json({err:true})
            } 
            else res.json({insertId:insertId})
        })
    })
})

app.post("/login",function(req,res){
    let user={
        username:req.body.username,
        password:req.body.password,
    }
    login(user,function(err,json){
        if(err){
            console.log(err)
            res.json(json);
        }else{
            res.json(json);
        }
    })
})

app.post("/register",function(req,res){
    let user={
        username:req.body.username,
        email:req.body.email,
        password:req.body.password,
    }
    console.log(req.body);
    register(user,function(err,insertId){
        if(err){
            console.log(err);
            res.json({error:true});
        } else res.json({insertId:insertId});
    })
})


app.listen(3000,()=>{
    console.log("app listening on port 3000")
})

function getBooksFromSection(section, callback){
    connection.query("SELECT * FROM (((((book INNER JOIN publisher ON book.publisher_id=publisher.publisher_id)INNER JOIN book_author ON "+
                    "book.book_id=book_author.book_id)INNER JOIN author ON book_author.author_id=author.author_id) INNER JOIN book_category ON "+
                    "book.book_id=book_category.book_id) INNER JOIN category ON book_category.category_id=category.category_id) WHERE category.category_id=? GROUP BY book.book_id",
                    [section],function(err,results,fields){
                        callback(err,results);
                    })
}


function getReviewsFromBook(book,callback){
    connection.query("SELECT * FROM book_review INNER JOIN language ON book_review.language_id=language.language_id WHERE book_id=?",[book],
        function(err,results,fields){
            callback(err,results);
        });
}

function postReview(review,callback){
    connection.query("INSERT INTO book_review VALUES (NULL,?,?,NOW(),?,?)",[review.reviewContent,review.rating,review.language,review.book],
        function(err,results,fields){
            console.log(err);
            callback(err,results.insertId)
        })
}


//Functions for the user management
//--------------------------------------------------------

function register(user,callback){
    console.log(user.password);
    let hash=bcrypt.hashSync(user.password,10);
    connection.query("INSERT INTO users VALUES (NULL,?,?,?)",[user.username,hash,user.email],function(err,results,fields){
        callback(err,results.insertId);
    })
}

function login(user,callback){
    connection.query("SELECT hash FROM users WHERE username=?",[user.username],function(err,results,fields){
        console.log(err);
        if(err) callback(err,{error:3,id:undefined,jwt:undefined});
        else if(results[0]){
            if(bcrypt.compareSync(user.password,results[0].hash)){
                callback(err,{error:0,id:results[0].id,jwt:jsonwebtoken.sign({username:user.username,password:user.password},"Mobile123")})
            }
            callback(err,{error:1,id:undefined,jwt:undefined});
        }else callback(err,{error:2,id:undefined,jwt:undefined})
    })
}

function loginRequired(req,res,next){
    if(req.user){
        next(req,res)
    }else{
        return res.status(401).json({ message: 'Unauthorized user!' });
    }
}


