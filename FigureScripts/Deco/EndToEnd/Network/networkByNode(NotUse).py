import os
import plotly.graph_objects as go
import plotly.io as pio
import plotly.express as px
import os
import sys
# Append parent directory to import path
sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))
import config
pio.kaleido.scope.mathjax = None


fig = go.Figure()
fig.add_trace(go.Bar(name="2-Node Cluster", x=["2-Node Cluster"], y=[11.89], legendrank=2, width=[0.8]
                     , text="11.9MB", textposition='outside', textfont = dict(size = 35)
                     , marker_color=config.disco))

fig.add_trace(go.Bar(name="Local", x=["Local"], y=[0.74], legendrank=4, width=[0.8]
                     , text="0.74MB", textposition='outside', textfont = dict(size = 30)
                     , marker_color=config.central))


fig.add_trace(go.Bar(name="Root", x=["Root"], y=[11.15], legendrank=1, width=[0.8]
                     , text="11.2MB", textposition='outside', textfont = dict(size = 30)
                     , marker_color=config.decoasy))





# fig.add_trace(go.Bar(name="DesisSw", x=[" "], y=[30545075.4], legendrank=4, width=[0.18]
#                      , marker_line_color='rgb(255,161,90)', marker_pattern_shape="-"))
# fig.update_traces(marker_color='rgb(158,202,225)', marker_line_color='rgb(8,48,107)', marker_line_width=1.5, opacity=0.6)


#legend
fig.update_layout(showlegend=False)
fig.update_layout(
    # legend=dict(
    #     yanchor="top",
    #     y=0.99,
    #     xanchor="left",
    #     x=0.01,
    #     # bordercolor="Black",
    #     # borderwidth=2,
    #     # bgcolor="white",
    #     font=dict(
    #         size=20,
    #         color="black"
    #     ),
    # ),
    yaxis=dict(
        title_text="bytes sent",
        titlefont=dict(size=35),
        exponentformat="e",
        ticktext=["0MB", "2MB", "4MB", "6MB", "8MB", "10MB", "12MB"],
        tickvals=[0, 2, 4, 6, 8, 10, 12],
        tickmode="array",
        range=[0, 13.2],
        ticks="inside",
        ticklen=20,
        tickwidth =5,
    ),
    xaxis=dict(
        # title_text="local nodes",
        # titlefont=dict(size=15),
        ticktext=[ "2-Node Cluster", "Local", "Root"],
        # tickvals=[1, 2, 3, 4],
        tickmode="array",
        # range=[0, 6],
    ),

)

# size & color
fig.update_layout(
    autosize=False,
    width=660,
    height=440,
    paper_bgcolor='rgba(0,0,0,0)',
    plot_bgcolor='rgba(0,0,0,0)',
    margin=dict(
        l=5,
        r=5,
        b=5,
        t=5,
        pad=0
    ),
)
# fig = px.bar(x=["a","b","c"], y=[1,3,2], color=["red", "goldenrod", "#00D"], color_discrete_map="identity")
fig.update_layout(barmode='group', bargap=0.4)

# fig.update_yaxes(automargin=True)
# fig.update_yaxes(ticks="outside", tickwidth=1, tickcolor='black', ticklen=5)
fig.update_xaxes(showline=True, linewidth=3, linecolor='black'#, mirror=True
                 , tickfont=dict(size=30))
fig.update_yaxes(showline=True, linewidth=3, linecolor='black'#, mirror=True
                 , tickfont=dict(size=35))
# fig.update_yaxes(showgrid=True, gridwidth=1, gridcolor='rgb(120,120,120)')
# ,tickfont_family="Arial Black"

fig.show()
# if not os.path.exists("E:\my paper\DesisPaper\Desis-Optimizing-Decentralized-Window-Aggregation\experiment\s1"):
#     os.mkdir("E:\my paper\DesisPaper\Desis-Optimizing-Decentralized-Window-Aggregation\experiment\s1")
# fig.write_image("images/fig1.svg")

pio.write_image(fig, "E:\On going Paper\Deco Efficient Decentralized Aggregation for Count-Based Windows\experiment\s1\/networkoverhead\/networkdiffer.pdf")
pio.write_image(fig, "E:\On going Paper\Deco Efficient Decentralized Aggregation for Count-Based Windows\experiment\s1\/networkoverhead\/networkdiffer.svg")