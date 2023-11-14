# Guide for Developers and Reviewers

## 1. Context

This document is intended to be used by developers and reviewers to ensure that the best practices are taken into account.
It contains a list of guidelines that should be followed when developing and reviewing both the code and the documentation.

## 2. Guide

### 2.1. Commits

#### 2.1.1. Commit Rule

When performing a commit, the commit message should follow the following rule:

"{JIRA_ISSUE_ID}: {[TAG]} - {Commit Message}"

Where: 
- {JIRA_ISSUE_ID} is the JIRA issue ID that the commit is related to.
- {[TAG]} is the user story tag that the commit is related to. It should be in the form of [TAG]. If the commit is not 
related to any user story, this part should be a general tag like [DOCS], [DM], etc...
- {Commit Message} is the commit message.

The "{}" should not be included in the commit message.

#### 2.1.2. When to Commit and Push

Commits should be done as often as possible, but not too often.
There is no specific rule for this.

Pushes should be done ONLY when the code is ready to be reviewed.
This means that the code should be working, and it is tested!

### 2.2. Documentation

#### 2.2.1. What to assure

    When making and reviewing the documentation, it should be assured that:

- SOLID principles are followed (You should go one by one!).
- GRASP principles are followed (You should go one by one!).
- The code is well documented.
- There is a good use of a variety of design patterns, and they are explicitly stated.
- There is at least one sequence diagram for each use case.
- There is a common class diagram for all the use cases, in the user story.
- The test cases cover all the use cases, in the different scenarios.

### 2.2. Code

#### 2.2.1. What to assure

- All the above-mentioned documentation rules should be followed.
- The code is coherent with the documentation.
- The commits were made in the right order and followed the commit rules.
- The code is well tested. This means that for each method there should be tests for the different scenarios.
- There are no public methods without javadoc.
- Complex code should be commented.
- There are no magic numbers (use constants)!.
- The code is well formatted and structured.
- There is no random commented code.
- The persistence.xml file and the UserApps shouldn't be changed.



