As soon as you install our application, the first thing you need to do, is to unzip the data.zip file,
and put it into the main PropertyAssessmentApplication(Make sure, the csv files are in only 1 folder, and not in
2 folders called data)

Our files:
1. Address, Coordinates, Neighbourhood, PropertyAssessment, PropertyAssessments are the files similar to what we had
for our individual work
2. default-view.fxml(located in Resources folder) is the file that strictly use for designing some items on the
screen of our application
3. PropertyAssessmentController - Crucial file that will contain most processes that are used for our application
Mostly for filtering, creating buttons and bars, etc...
4. MapGraphicsManager - file that will contain our ARCGIS map set-up
5. PropertyAssessmentApplication - our main file of the application that you will need to run to open the application
you need to make sure that csv files are unzipped and then it will launch.

The application itself:
1. As soon as you launch our application you will see this: ARCGIS map in the middle, left side will be our filters,
legend of the app and Text window where you will see the information of the chosen property. On the right side you
will see another menu bar with statistics, historical Data, Trends and then the list that will provide information about
each household in i.e. Condo. So if the house consists of multiple apartments, you will have information about each of
them shown there.
2. You can pick any of the working Filters, either it is Wards, property value, Garage, Neighbourhood or Property Assessment
Class. For each of them you can pick only one option, except of Property value. For property value, you should be able
to choose multiple options and all of the chosen requirements will be used to provide the output for you.
3. When you enter the requirements and press enter, you will generate points on the map, as well as legend of prices
When you click on any of the points on the map, you will get all the information about that property shown on the
bottom left corner of your screen(you can copy that information by pressing the button underneath).
And you will see all the information on the right side of the screen showing up for you, such as Statistics, Historical
data and Trends. As well as the Assessments details for apartments.
4.To clear your search completely, just simply press the button clear on the left side of the screen. It will reset
the map, as well as all the filters that were applied earlier.
5. Also, when you click on any of the empty spots on the map, the application will create an output in your IntellIJ,
that there's no graphics found at the clicked location. So it indicates whether you click on the active property
or not.

