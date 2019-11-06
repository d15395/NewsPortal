#!/usr/bin/env bash

sam build && \
sam package --s3-bucket tesseract-news-sam --output-template-file packaged.yaml && \
sam deploy --template-file ./packaged.yaml \
 --s3-bucket tesseract-news-sam \
 --stack-name NewsAPI \
 --capabilities CAPABILITY_IAM