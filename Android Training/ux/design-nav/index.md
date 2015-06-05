# 设计高效的导航

One of the very first steps to designing and developing an Android application is to determine (决定) what users are able to see and do with the app. Once you know what kinds of data users are interacting with in the app, the next step is to design the interactions that allow users to navigate across, into, and back out from the different pieces of content within the app.(设计开发 App 的起初步骤之一就是决定用户能够在App上看到什么和做什么。一旦你知道用户在App上和哪种内容互动，下一步就是去设计容许用户在 App 的不同内容块间切换，进入，回退的交互。)

This class shows you how to plan out the high-level screen hierarchy for your application and then choose appropriate forms of navigation to allow users to effectively and intuitively traverse your content. Each lesson covers various stages in the interaction design process for navigation in Android applications, in roughly chronological order. After going through the lessons in this class, you should be able to apply the methodology and navigation paradigms outlined here to your own applications, providing a coherent navigation experience for your users.(本课程演示如何为你的应用规划出高标准的界面层次，然后为它选择适宜的导航形式来允许用户高效而直观的浏览内容。按粗略的先后顺序,每堂课涵盖Android应用导航交互设计过程中的不同阶段。学过这些课之后，你应该可以应用这些列出的方法和设计范例到你自己的应用中，为你的用户提供一致的导航体验了。)

## Lessons

* [**规划界面和他们之间的关系**](screen-planning.md)

  Learn how to choose which screens your application should contain. Also learn how to choose which screens should be directly reachable from others. This lesson introduces a hypothetical news application to serve as an example for later lessons.(学习如何选择你应用应该包含的界面。并且学习如何选择其他界面可直达的界面。这节课介绍了一个假想的新闻应用为以后课程作例子。)


* [**为多种大小的屏幕进行规划**](multi-sizes.md)

  Learn how to group (组织) related（相关的） screens together on larger-screen devices to optimize use of available screen space.(学习如何在大屏设备上组合相关界面来优化用户可视界面空间。)


* [**提供向下和横向导航**](descendant-lateral.md)

  Learn about techniques for allowing users to navigate deep into, as well as across, your content hierarchy. Also learn about pros and cons of, and best practices for, specific navigational UI elements for various situations.（学习容许用户深入某一层或者在内容层次间横跨的技巧。而且学习一些特定导航 UI 元素在不同情景下的优缺点和最佳用法。）


* [**提供向上和历史导航**](ancestral-temporal.md)

  Learn how to allow users to navigate upwards in the content hierarchy. Also learn about best practices for the Back button and temporal navigation, or navigation to previous screens that may not be hierarchically related.(学习如何容许用户在内容层级向上导航。并且学习 Back 键和历史导航的最佳做法，也即导航到和层次无关的之前的画面。)


* [**综合：设计样例 App**](wireframing.md)

  Learn how to create screen wireframes (low-fidelity graphic mockups) representing the screens in a news application based on the desired information model. These wireframes utilize navigational elements discussed in previous lessons to demonstrate intuitive and efficient navigation.（学习如何创建界面的 Wireframe（线框图，模糊的图形模型）来代表新闻应用基于设想信息模型的界面。这些 Wireframe 利用上述课程讨论的导航元件来展示直观高效导航。）
