# TreeListPicker
Tree list picker realization for Android

# DataProvider
## File system data provider example
### ListItem implementation

```kotlin
open class FileItem(val file: File) : ListItem {
    override val isDirectory: Boolean
        get() = file.isDirectory

    override val name: String
        get() = file.name
}
```
### DataProvider implementation
```kotlin
class FSDataProvider : DataProvider<FileItem> {
    private val rootFolder = Environment.getExternalStorageDirectory()

    override suspend fun getRoot(): List<FileItem> =
            rootFolder.listFiles().map { FileItem(it) }

    override suspend fun getByParent(listItem: FileItem): List<FileItem> =
            listItem.file.listFiles().map { FileItem(it) }
}

```

### Usage example
```kotlin
TreeListDialogBuilder(this, FSDataProvider()).apply {
    title = "Select a folder"
    positiveButtonTitle = "Select"
    positiveButtonClick = {doSomethind(it)}
}.build().show()
```

