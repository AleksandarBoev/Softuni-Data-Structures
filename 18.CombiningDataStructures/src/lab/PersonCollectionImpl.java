package lab;

import java.util.*;

public class PersonCollectionImpl implements PersonCollection {

    private HashMap<String, Person> emailPerson;
    private HashMap<String, TreeSet<Person>> emailDomainPerson;
    private HashMap<NameTown, TreeSet<Person>> nameTownPerson;
    private TreeMap<Integer, TreeSet<Person>> agePerson;
    private HashMap<String, TreeMap<Integer, TreeSet<Person>>> townAgePerson;

    public PersonCollectionImpl() {
        emailPerson = new HashMap<>();
        emailDomainPerson = new HashMap<>();
        nameTownPerson = new HashMap<>();
        agePerson = new TreeMap<>();
        townAgePerson = new HashMap<>();
    }

    @Override
    public boolean addPerson(String email, String name, int age, String town) {
        if (emailPerson.containsKey(email)) {
            return false;
        }

        Person newPerson = new Person(email, name, age, town);

        emailPerson.put(email, newPerson);

        String emailDomain = getDomain(email);
        if (!emailDomainPerson.containsKey(emailDomain)) {
            emailDomainPerson.put(emailDomain, new TreeSet<>());
        }
        emailDomainPerson.get(emailDomain).add(newPerson);

        NameTown nameTown = new NameTown(name, town);
        if (!nameTownPerson.containsKey(nameTown)) {
            nameTownPerson.put(nameTown, new TreeSet<>());
        }
        nameTownPerson.get(nameTown).add(newPerson);

        if (!agePerson.containsKey(age)) {
            agePerson.put(age, new TreeSet<>(new PersonComparatorByAgeThenEmail()));
        }
        agePerson.get(age).add(newPerson);

        if (!townAgePerson.containsKey(town)) {
            townAgePerson.put(town, new TreeMap<>());
        }
        if (!townAgePerson.get(town).containsKey(age)) {
            townAgePerson.get(town).put(age, new TreeSet<>(new PersonComparatorByAgeThenEmail()));
        }
        townAgePerson.get(town).get(age).add(newPerson);

        return true;
    }

    @Override
    public int getCount() {
        return emailPerson.size();
    }

    @Override
    public Person findPerson(String email) {
        return emailPerson.get(email);
    }

    @Override
    public boolean deletePerson(String email) {
        Person personToRemove = emailPerson.remove(email);

        if (personToRemove == null) {
            return false;
        }

        String emailDomain = getDomain(email);
        emailDomainPerson.get(emailDomain).remove(personToRemove);

        nameTownPerson.get(new NameTown(personToRemove.getName(), personToRemove.getTown())).remove(personToRemove);

        agePerson.get(personToRemove.getAge()).remove(personToRemove);

        townAgePerson.get(personToRemove.getTown()).get(personToRemove.getAge()).remove(personToRemove);

        return true;
    }

    @Override
    public Iterable<Person> findPersons(String emailDomain) {
        return getEmptyCollectionIfNull(emailDomainPerson.get(emailDomain));
    }

    @Override
    public Iterable<Person> findPersons(String name, String town) {
        return getEmptyCollectionIfNull(nameTownPerson.get(new NameTown(name, town)));
    }

    @Override
    public Iterable<Person> findPersons(int startAge, int endAge) {
        return findPersons(startAge, endAge, this.agePerson);
    }

    @Override
    public Iterable<Person> findPersons(int startAge, int endAge, String town) {
        var agePerson = townAgePerson.get(town);

        if (agePerson == null) {
            return new ArrayList<>(0);
        }

        return findPersons(startAge, endAge, agePerson);
    }

    private Iterable<Person> getEmptyCollectionIfNull(Collection<Person> collection) {
        return collection == null ? new ArrayList<>(0) : collection;
    }

    private String getDomain(String email) {
        return email.substring(email.indexOf('@') + 1);
    }

    private Iterable<Person> findPersons(int startAge, int endAge, TreeMap<Integer, TreeSet<Person>> agePerson) {
        Integer endAgeBound = agePerson.higherKey(endAge);

        if (endAgeBound == null) {
            endAgeBound = endAge;
        }

        //submap is inclusive at first key and exclusive at second key
        SortedMap<Integer, TreeSet<Person>> inAgeRangePeople = agePerson.subMap(startAge, endAgeBound);

        ArrayList<Person> result = new ArrayList<>();

        inAgeRangePeople.forEach((age, treeSet) -> {
            result.addAll(treeSet);
        });

        return result;
    }

    private class NameTown extends Object {
        String name;
        String town;

        NameTown(String name, String town) {
            this.name = name;
            this.town = town;
        }

        //TODO what if they are both null?
        @Override
        public int hashCode() {
            if (name == null) {
                return town.hashCode();
            } else if (town == null){
                return name.hashCode();
            } else {
                return name.hashCode() + town.hashCode();
            }
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }

            if (!(obj instanceof NameTown)) {
                return false;
            }

            NameTown nameTownObj = (NameTown) obj;
            return this.name.equals(nameTownObj.name) && this.town.equals(nameTownObj.town);
        }
    }

    private class PersonComparatorByAgeThenEmail implements Comparator<Person> {

        @Override
        public int compare(Person person1, Person person2) {
            int comparisonResult = Integer.compare(person1.getAge(), person2.getAge());

            if (comparisonResult == 0) {
                comparisonResult = person1.getEmail().compareTo(person2.getEmail());
            }

            return comparisonResult;
        }
    }
}
