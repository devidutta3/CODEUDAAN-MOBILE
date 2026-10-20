package com.example.data

object OfficialCommunityLinks {
    const val GITHUB_URL = "https://github.com/codeudaan"
    const val LINKEDIN_URL = "https://www.linkedin.com/in/code-udaan-7020983b3/"
    const val WHATSAPP_URL = "https://chat.whatsapp.com/KGpM4DSpQCQBnsFwHNX8Xh"
    const val WEBSITE_URL = "https://codeudaan.ai.studio/"
}

data class CourseModule(
    val title: String,
    val description: String,
    val duration: String
)

data class Course(
    val id: String,
    val title: String,
    val category: String,
    val level: String,
    val description: String,
    val fullOverview: String,
    val totalHours: String,
    val modulesCount: Int,
    val modules: List<CourseModule>,
    val highlights: List<String>
)

data class PracticeQuestion(
    val id: String,
    val title: String,
    val description: String,
    val exampleInput: String,
    val exampleOutput: String,
    val difficulty: String, // "Easy", "Medium", "Hard"
    val language: String, // "C", "Python", "JavaScript"
    val topic: String,
    val starterCode: String,
    val explanation: String
)

data class CommunityPost(
    val id: String,
    val author: String,
    val authorRole: String,
    val category: String, // "Announcement", "Showcase", "Discussion", "Workshop"
    val title: String,
    val content: String,
    val date: String,
    val likesCount: Int,
    val commentsCount: Int,
    val isDemoContent: Boolean = true
)

data class CodeUdaanEvent(
    val id: String,
    val title: String,
    val date: String,
    val time: String,
    val location: String,
    val category: String, // "Workshop", "Webinar", "Hackathon", "Session"
    val description: String,
    val speaker: String,
    val isPast: Boolean = false
)

data class SocialLink(
    val platform: String,
    val url: String,
    val actionLabel: String,
    val description: String,
    val iconType: String
)

data class Badge(
    val title: String,
    val iconName: String,
    val description: String,
    val dateEarned: String
)

object SampleData {

    val courses = listOf(
        Course(
            id = "c_prog",
            title = "C Programming Fundamentals",
            category = "Core Languages",
            level = "Beginner Friendly",
            description = "Master variables, control structures, pointers, memory allocation, and problem-solving in C.",
            fullOverview = "CodeUdaan's foundation course designed specifically for absolute beginners. Dive deep into procedural programming, algorithm logic, and core syntax essential for every engineer.",
            totalHours = "12 Hours",
            modulesCount = 6,
            modules = listOf(
                CourseModule("Module 1: Introduction & Environment", "Setting up GCC, writing your first Hello World program in C.", "1.5 hrs"),
                CourseModule("Module 2: Variables & Data Types", "Integers, floats, chars, standard input/output formatting.", "2 hrs"),
                CourseModule("Module 3: Conditionals & Loops", "if-else, switch, for, while, and do-while loop mechanics.", "2.5 hrs"),
                CourseModule("Module 4: Functions & Scope", "Modular coding, parameter passing by value, recursive logic.", "2 hrs"),
                CourseModule("Module 5: Arrays & Strings", "Single and multi-dimensional arrays, string manipulation functions.", "2 hrs"),
                CourseModule("Module 6: Pointers & Memory", "Addresses, dereferencing, dynamic memory (malloc, free).", "2 hrs")
            ),
            highlights = listOf(
                "First step for CodeUdaan's active training sessions",
                "Hands-on memory model concepts",
                "Foundation for Data Structures & Algorithms"
            )
        ),
        Course(
            id = "python_prog",
            title = "Python Programming",
            category = "High-Level Languages",
            level = "Beginner Friendly",
            description = "Learn Python syntax, lists, dictionaries, OOP principles, and automation basics.",
            fullOverview = "Explore Python's readable syntax, rich standard library, and versatile application from scripting to web and data analysis.",
            totalHours = "15 Hours",
            modulesCount = 5,
            modules = listOf(
                CourseModule("Module 1: Python Basics", "Syntax, variables, input/output, type casting.", "2 hrs"),
                CourseModule("Module 2: Data Structures", "Lists, Tuples, Sets, and Dictionaries in action.", "3 hrs"),
                CourseModule("Module 3: Functional Programming", "Lambda, map, filter, list comprehensions.", "3 hrs"),
                CourseModule("Module 4: Object-Oriented Python", "Classes, inheritance, encapsulation, and magic methods.", "3.5 hrs"),
                CourseModule("Module 5: File I/O & Modules", "Reading/writing files, importing libraries, error handling.", "3.5 hrs")
            ),
            highlights = listOf(
                "Clean, readable syntax",
                "Versatile usage across domain fields",
                "Rapid prototyping and scripting"
            )
        ),
        Course(
            id = "html_basics",
            title = "HTML5 Web Structure",
            category = "Web Development",
            level = "Beginner Friendly",
            description = "Build structured web pages using semantic HTML tags, forms, tables, and media.",
            fullOverview = "Learn the essential building blocks of the web. Structure documents properly for accessibility and search engines.",
            totalHours = "6 Hours",
            modulesCount = 4,
            modules = listOf(
                CourseModule("Module 1: HTML Architecture", "DOCTYPE, head, body, headings, paragraphs.", "1.5 hrs"),
                CourseModule("Module 2: Links & Media", "Hyperlinks, images, audio, video tags.", "1.5 hrs"),
                CourseModule("Module 3: Web Forms & Inputs", "Form validation, text inputs, radio buttons, select dropdowns.", "1.5 hrs"),
                CourseModule("Module 4: Semantic Layouts", "header, nav, main, section, article, footer tags.", "1.5 hrs")
            ),
            highlights = listOf(
                "Semantic Web Standards",
                "Accessibility fundamentals",
                "Interactive form design"
            )
        ),
        Course(
            id = "css_design",
            title = "CSS3 Modern Styling",
            category = "Web Development",
            level = "Beginner Friendly",
            description = "Master layout systems with Flexbox, CSS Grid, responsive design, and animations.",
            fullOverview = "Transform plain HTML into modern, beautiful user interfaces with CSS positioning, colors, and responsive layouts.",
            totalHours = "8 Hours",
            modulesCount = 4,
            modules = listOf(
                CourseModule("Module 1: Box Model & Typography", "Margin, border, padding, Google fonts, text styling.", "2 hrs"),
                CourseModule("Module 2: Flexbox Layouts", "Alignment, direction, wrapping, space distribution.", "2 hrs"),
                CourseModule("Module 3: CSS Grid", "Grid tracks, areas, responsive auto-fit layouts.", "2 hrs"),
                CourseModule("Module 4: Transitions & Animations", "Keyframes, hover effects, smooth transform effects.", "2 hrs")
            ),
            highlights = listOf(
                "Flexbox & CSS Grid mastery",
                "Mobile-first responsive design",
                "Smooth micro-interactions"
            )
        ),
        Course(
            id = "js_interactive",
            title = "JavaScript Fundamentals",
            category = "Web Development",
            level = "Intermediate",
            description = "Bring web pages to life with DOM manipulation, ES6+ features, events, and Promises.",
            fullOverview = "The programming language of the modern web. Learn asynchronous operations, event handling, and ES6 modern standards.",
            totalHours = "14 Hours",
            modulesCount = 5,
            modules = listOf(
                CourseModule("Module 1: JS Fundamentals", "let/const, arrow functions, template literals.", "2.5 hrs"),
                CourseModule("Module 2: DOM Manipulation", "Selecting elements, updating styles, event listeners.", "3 hrs"),
                CourseModule("Module 3: Asynchronous JS", "Callbacks, Promises, async/await, Fetch API concepts.", "3 hrs"),
                CourseModule("Module 4: Modern ES6 Features", "Destructuring, spread/rest operators, modules.", "2.5 hrs"),
                CourseModule("Module 5: Small Web Projects", "Building interactive tools (Calculator, Todo list).", "3 hrs")
            ),
            highlights = listOf(
                "Dynamic DOM manipulation",
                "Modern ES6 syntax",
                "Asynchronous programming"
            )
        ),
        Course(
            id = "ai_ml_intro",
            title = "AI & Machine Learning",
            category = "Advanced Tech",
            level = "Intermediate",
            description = "Explore foundational concepts of AI, supervised learning, neural networks, and prompt engineering.",
            fullOverview = "Understand how modern AI systems learn from data. Demystify Machine Learning models, Neural Networks, and Generative AI applications.",
            totalHours = "16 Hours",
            modulesCount = 5,
            modules = listOf(
                CourseModule("Module 1: Introduction to AI", "What is AI, ML, Deep Learning? Data fundamentals.", "2.5 hrs"),
                CourseModule("Module 2: Supervised Learning", "Linear Regression, Classification, Model Training.", "3.5 hrs"),
                CourseModule("Module 3: Unsupervised & Clustering", "K-Means clustering, Pattern discovery.", "3 hrs"),
                CourseModule("Module 4: Intro to Neural Networks", "Perceptrons, activation functions, basic deep learning.", "3.5 hrs"),
                CourseModule("Module 5: Generative AI & Prompting", "LLMs, prompt engineering, AI ethics.", "3.5 hrs")
            ),
            highlights = listOf(
                "Supervised & Unsupervised ML concepts",
                "Neural Network basics",
                "Generative AI & Prompt Engineering"
            )
        )
    )

    val practiceQuestions: List<PracticeQuestion> by lazy {
        QuestionBankGenerator.generateAllQuestions()
    }

    val communityPosts = listOf(
        CommunityPost(
            id = "post_1",
            author = "CodeUdaan Core Team",
            authorRole = "Community Lead",
            category = "Announcement",
            title = "Welcome to CodeUdaan - Static Android Learning Hub!",
            content = "We are thrilled to launch the official CodeUdaan learning app. Our core mission is empowering students with strong coding fundamentals, hands-on projects, and a collaborative tech community.",
            date = "Oct 18, 2026",
            likesCount = 42,
            commentsCount = 12
        ),
        CommunityPost(
            id = "post_2",
            author = "Priya Sharma",
            authorRole = "Student Member",
            category = "Showcase",
            title = "Student Project Showcase: C Matrix Operations Calculator",
            content = "Hey everyone! Built a CLI-based C program for Matrix Addition, Multiplication, and Transpose as part of CodeUdaan C Training Session 1. Check out the clean pointer implementation!",
            date = "Oct 19, 2026",
            likesCount = 28,
            commentsCount = 7
        ),
        CommunityPost(
            id = "post_3",
            author = "Rahul Verma",
            authorRole = "Peer Mentor",
            category = "Discussion",
            title = "Tips for Mastering Pointers in C Programming",
            content = "Struggling with pointers? Remember: a pointer variable stores the memory address of another variable. Use '&' to get address and '*' to dereference. Draw memory blocks on paper—it helps immensely!",
            date = "Oct 19, 2026",
            likesCount = 35,
            commentsCount = 15
        ),
        CommunityPost(
            id = "post_4",
            author = "CodeUdaan Event Desk",
            authorRole = "Coordinator",
            category = "Workshop",
            title = "Upcoming Workshop: Getting Started with Web Development",
            content = "Join our upcoming live session on HTML, CSS, and JavaScript basics! We will build a live portfolio site from scratch. Mark your calendar for this Saturday.",
            date = "Oct 20, 2026",
            likesCount = 50,
            commentsCount = 19
        )
    )

    val events = listOf(
        CodeUdaanEvent(
            id = "evt_1",
            title = "C Programming Kickoff Training Session",
            date = "Saturday, Oct 25, 2026",
            time = "5:00 PM - 6:30 PM IST",
            location = "CodeUdaan Virtual Classroom",
            category = "Workshop",
            description = "Interactive beginner training session covering environment setup, GCC compiler, variables, and logic building in C.",
            speaker = "Rahul Dev (Core Mentor)"
        ),
        CodeUdaanEvent(
            id = "evt_2",
            title = "Web Development Bootcamp: HTML & CSS Essentials",
            date = "Wednesday, Nov 01, 2026",
            time = "6:00 PM - 7:30 PM IST",
            location = "Online Live Stream",
            category = "Webinar",
            description = "Learn how to structure web pages with semantic HTML5 and design visually stunning responsive pages with CSS Flexbox.",
            speaker = "Priya Sharma (Frontend Developer)"
        ),
        CodeUdaanEvent(
            id = "evt_3",
            title = "AI & ML Orientation: demystifying Generative AI",
            date = "Sunday, Nov 12, 2026",
            time = "4:00 PM - 5:30 PM IST",
            location = "CodeUdaan Discord / Meet",
            category = "Session",
            description = "Explore foundational concepts of artificial intelligence, neural networks, and how prompt engineering shapes modern tools.",
            speaker = "Dr. A. K. Sundaram (AI Researcher)"
        ),
        CodeUdaanEvent(
            id = "evt_4",
            title = "CodeUdaan Annual Student Hackathon 2026",
            date = "Dec 05 - Dec 06, 2026",
            time = "36 Hours Weekend Event",
            location = "Hybrid (Campus & Online)",
            category = "Hackathon",
            description = "Build innovative software solutions in C, Python, or Web Tech. Win mentorship badges, project showcases, and certificate of excellence.",
            speaker = "CodeUdaan Executive Team"
        ),
        CodeUdaanEvent(
            id = "evt_5",
            title = "Past Event: Intro to Logic Building & Algorithms",
            date = "Oct 10, 2026",
            time = "Completed",
            location = "Online Classroom",
            category = "Session",
            description = "A introductory session on pseudocode, flowchart creation, and problem decomposition for first-year learners.",
            speaker = "CodeUdaan Mentors",
            isPast = true
        )
    )

    val socialLinks = listOf(
        SocialLink(
            platform = "GitHub",
            url = OfficialCommunityLinks.GITHUB_URL,
            actionLabel = "View GitHub Profile",
            description = "Explore open-source student repositories, code samples, and practice challenges.",
            iconType = "github"
        ),
        SocialLink(
            platform = "LinkedIn",
            url = OfficialCommunityLinks.LINKEDIN_URL,
            actionLabel = "View LinkedIn Profile",
            description = "Connect with CodeUdaan, stay updated on professional events, and network.",
            iconType = "linkedin"
        ),
        SocialLink(
            platform = "WhatsApp Community",
            url = OfficialCommunityLinks.WHATSAPP_URL,
            actionLabel = "Join WhatsApp Group",
            description = "Join official student discussion group for real-time announcements & updates.",
            iconType = "whatsapp"
        ),
        SocialLink(
            platform = "Official Website",
            url = OfficialCommunityLinks.WEBSITE_URL,
            actionLabel = "Visit Official Website",
            description = "Visit our official platform portal for resources, updates, and learning paths.",
            iconType = "website"
        )
    )

    val defaultBadges = listOf(
        Badge("Code Explorer", "rocket", "Explored CodeUdaan learning modules and practice questions.", "Current Session"),
        Badge("C Pioneer", "code", "Started learning C programming fundamentals.", "Current Session"),
        Badge("Community Member", "groups", "Joined the CodeUdaan learning community.", "Current Session")
    )
}
