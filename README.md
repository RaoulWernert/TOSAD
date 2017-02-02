# TOSAD

Adding a new Ruletype:

JAVA
- Create a new RuleType Class.
- The class must extend BusinessRule.
- In the generator must be added a function with the created ruletype class as parameter.
- In the Rulefactory and the RuleTypeEnum the new Ruletype must be added.

APEX

- A new page must be created for the report for the new RuleType
- A new page must be created so that the right values can be filled in.
- If needed, new columns must be added to the BusinessRules Table.
- A new entry must be created in the Ruletypes  table with the specific Rule Date (Name, Code, etc)