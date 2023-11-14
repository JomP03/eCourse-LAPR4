# Domain Model

![Domain Model](DomainModel.svg)

<br>

## Aggregate Justification

Generally speaking, when we try to identify an aggregate, we should have in mind some key aspects such as:

- Indentifying the business key concepts.

- Define rules that will allow our understanding of how the concepts interact with each other;

- Define transactional limits, in other words, the aggregates must be drawn to encapsulate and atenuate this limits;

In a nutshell, we try to keep related objects frequently between the same aggregate, that are related with what's outside them, mostly by the root of that aggregate.

Insinde a business where eCourse is included, we define as principal aggregates the following ones:
"CourseAggregate", "ExamAggregate", "TakenExamAggregate", "PostitRecordAggregate", "PostitAggregate", "SharedBoardAggregate", "UserAggregate", "MeetingAggregate" and "ClassAggregate".

An example that shows the direct application of these principles is the selection of the "ExamAggregate". An exam is a structured set of questions with some key attributes.
Since there's no need to have an interaction between these attributes and the "outside world", we encapsulate this value objects inside the "ExamAggregate", being this the root.
Another clear example is the users of the eCourse example, where from a transactional perspective, wouldn't be a senseful thing to encapsulate users with a Shared Board, and because of that we decided to place this two key concepts in two different aggregates, allowing the single manipulation of each one, and making the communication between them only possible through their roots.

Glossary

File containing a brief description for each domain model concept: [Glossary](glossary.md)