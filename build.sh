#!/usr/bin/env bash

mvn clean package
mvn dockerfile:build dockerfile:push