import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import * as AWS from 'aws-sdk/global';
import * as S3 from 'aws-sdk/clients/s3';
import { Observable, BehaviorSubject } from 'rxjs';
import { PostService } from './post.service';

@Injectable({
  providedIn: 'root'
})
export class UploadService {
  private bucket;

  // behavior subject
  private data;

  // Updated with my s3

  constructor(private httpCli: HttpClient) {
    this.bucket = new S3({
      accessKeyId: '',
      secretAccessKey: '',
      region: '',
      apiVersion: "",
      params: { Bucket: "" }
    });

    let bucketRegion = "";
    let IdentityPoolId = "";

    AWS.config.update({
      region: bucketRegion,
      credentials: new AWS.CognitoIdentityCredentials({
        IdentityPoolId: IdentityPoolId
      })
    });

    this.data = new BehaviorSubject<string>(this.data);
    this.data = this.data.asObservable();
    this.data = this.data.toPromise();
   }

  doUpload(file)
  {
    
    let currentUser = JSON.parse(localStorage.getItem("currentUser"));
    
    const filename = `https://40forty.s3.amazonaws.com/${currentUser.username}-${file.name}`;

    const fileKey = `${currentUser.username}-${file.name}`;
    const contentType = file.type;
    
    const params = {
      Bucket: "40forty",
      Key: fileKey,
      Body: file,
      ACL: 'public-read',
      ContentType: contentType
    };

    if(this.bucket.headObject({ Key: fileKey }, (err, data) => 
    {
      if(!err) {
        return "ERROR : File already exists"
      }
      if (err.code !== "NotFound") {
        return "ERROR uploading file: " + err.message;
      }
    }))
    
    this.bucket.putObject({ Key: fileKey }, (err, res) => 
    {
      if(err) {
        return "ERROR uploading file: " + err.message;
      }

      return filename;
    });

    return "ERROR idk";
  }
}





