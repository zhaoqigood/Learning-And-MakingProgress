Team Member: Kebo Duan   Qi Zhao

Project: This is a Face recognition Website. 

    Three functions: The User could upload its face photos to the website and get the face analysis result such as emotion, sex and so on.
                     The User could upload its face photos and names which will be stored in the S3 bucket and database.
                     The User could upload face photos being recognized by the website, and get the name of the people who appear in the                        photo. 
                   
Work Specialization:

     Kebo Duan:   Building the backend of this website, including the AWS lambda function, S3 bucket, DynamoDB, API Gateway.
                  Implement the Face-name pair storage and face recognition in the backend.
                  
     Qi Zhao:     Building part of the backend and frontend of this website, including the AWS lambda function, DynamoDB, static webpage content(html) and dynamic function(javascript). 
                  Implement the Face-analysis function in the frontend.

Files Specification:

     There are mainly two files in this project:

     face_recognition.py : this is a python file that contains the backEnd code.

     index.html: this is a html file that contains the frontEnd code.
     
The web URL is:
 http://machine-learning-website.s3-website-us-east-1.amazonaws.com
 
Face recognition functionality is invalid because we have shut down the relevant AWS service.
