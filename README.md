# Installation
To get a Git project into your build:

Step 1. Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:

```groovy
    allprojects {
	repositories {
	    ...
	    maven { url 'https://jitpack.io' }
	}
    }
```
Step 2. Add the dependency

```groovy
    dependencies {
	implementation 'com.github.eosobande:easy-recyclerview-adapter:1.0'
    }
```

# How to use
1. Create your new adapter class and extend EasyRecyclerAdapter which takes 2 generic params. 
One being the data model class to be stored within the adapter class and the second being the Viewholder class
```java
public class NewAdapter extends EasyRecyclerAdapter<String, NewAdapter.ViewHolder> {}
```
2. Implement the required methods from the superclass

a) getLayoutRes: this method returns the resource id for the layout resource file
```java
    @Override
    protected int getLayoutRes() {
        return R.layout.resourceItem;
    }
```

b) displayContent: here is where you display the data for each item in the recycler view
```java
    @Override
    protected void displayContent(ViewHolder holder, String s, int position) {
        holder.title.setText(s);
    }
```

c) onItemClick: here is where you handle the click event on each item
```java
    @Override
    public void onItemClick(View view, String s, int position) {
        // do something with the string s
    }
```
NOTE: you can make both this method and the class abstract and override this method in the class instantiation e.g
```java
    new NewAdapter() {
        @Override
        public void onItemClick(View view, String s, int position) {
            // do something with the string s
        }
    }
```

d) onCreateViewHolder: here you instantiate your custom viewholder class for each item in the list. 
Passing the return value of the inflate method into the constructor, the inflate method returns a view 
inflated from the layout resource designated in getLayoutRes 
```java
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
        return new ViewHolder(inflate(viewGroup)) {
            @Override
            public void onClick(View v) {
                onItemClick(v, getItem(i), i);
            }
        };
    }
```
NOTE: here we override the onClick method from our view holder because it was declared as abstract 
and implements the View.OnClickListener interface. In order for the adapter class to handle click events on each item,
it's onItemClick method is called inside the onClick method, passing the view, the model instance for the position, 
and the position clicked on. getItem method returns the model instance for the particular position.
```java
    abstract class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    
        TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
        }

    }
```

e) onFilterCondition: this serves as a search function on the list, you implement the search condition necessary 
here using the search term (filter).
```java
    @Override
    protected boolean onFilterCondition(String s, String filter) {
        return s.tolowercase().contains(filter);
    }
```

3. Instantiate the adapter class
```java
    NewAdapter adapter = new NewAdapter();
```
OR if you decide to override the onItemClick method during instantiation
```java
    NewAdapter adapter = new NewAdapter() {
        @Override
        public void onItemClick(View view, String s, int position) {
            // do something with the string s
        }
    }
```

4. Assign the adapter to the recycler view
```java
    recyclerView.setAdapter(adapter)
```

5. Assign items
```java
// use the add or addAll methods to assign items to the recycler view
	adapter.add("Emmanuel"); // add to end of list
	adapter.add("Michael", 0); // add to position 0
	adapter.replace("Rafael", 0); // change the item in a specific position
	addAll(namesArrayList) // add a list of items
	replaceAll(newNamesArrayList) // replace all items on list with new ones
	remove(0); // remove item 0 from recycler view
```

6. Filter the recycler view
```java
    adapter.filter("Emmanuel");
```

